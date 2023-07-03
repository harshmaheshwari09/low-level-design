package com.switchcase.games.model;

import com.switchcase.database.model.Database;
import com.switchcase.games.util.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public abstract class GameController implements Game {
    GameBase gameBase;
    List<Player> players;
    Properties gameProperties;

    //============ getter-setter ================//
    public GameBase getGameBase() {
        return gameBase;
    }

    public List<Player> getPlayers() {
        return players;
    }

    //============ constructors ================//
    public GameController() throws Exception {
        loadGameProperties();
        players = new ArrayList<>();
    }

    //============ key-operations  ================//
    private void loadGameProperties() throws Exception {
        this.gameProperties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(getAbsolutePropertyFilePath())) {
            gameProperties.load(fileInputStream);
        } catch (Exception exception) {
            throw new Exception(ExceptionReason.UNABLE_TO_LOAD_GAME.getMessage());
        }
    }

    @Override
    public void startGame() throws Exception {
        Set<String> savedGames = getFilesInDirectory(getSaveDirectoryPath());
        if (savedGames.size() > 0 && getUserInputForGameStartup() == 1) {
            loadGame(savedGames);
        } else {
            startNewGame();
        }
    }

    private void loadGame(Set<String> savedGames) throws Exception {
        StringBuilder displayMessage = new StringBuilder();
        displayMessage.append("Please select the game you want to play: \n");
        for (String savedGame : savedGames) {
            displayMessage.append(savedGame).append("\n");
        }
        String savedGame =
            ConsoleManager.getUserInput(displayMessage.toString(), input -> {
                if (savedGames.contains(input.trim())) {
                    return input.trim();
                }
                throw new RuntimeException();
            });
        Path gamePath = getSaveDirectoryPath().resolve(savedGame);
        List<String> gameFiles = getFilesInDirectory(gamePath).stream().collect(Collectors.toList());
        Collections.sort(gameFiles);
        for (String gameFile : gameFiles) {
            String gameFilePath = gamePath.resolve(gameFile).toString();
            if (gameFile.equals(gameProperties.getProperty(GameProperties.GAME_BASE_FILE_NAME))) {
                gameBase = (GameBase) Database.loadData(gameFilePath);
            } else {
                Player currentPlayer = (Player) Database.loadData(gameFilePath);
                players.add(currentPlayer);
            }
        }
        removePreviousSavedFile(gamePath.toString());
        playGame();
    }

    private void removePreviousSavedFile(String path) {
        File directory = new File(path);
        removeDirectory(directory);
    }

    private void removeDirectory(File directory) {
        File[] files = directory.listFiles();
        for (var file : files) {
            if (file.isDirectory()) {
                removeDirectory(file);
            } else {
                file.delete();
            }
        }
        directory.delete();
    }

    private void startNewGame() throws Exception {
        ConsoleManager.print("Starting new game!\n");
        initializeGameBase();
        initializePlayers();
        performOrdering();
        playGame();
    }

    private void playGame() throws Exception {
        Deque<Player> queue = new LinkedList<>(players);
        List<Player> leaderBoard = new ArrayList<>();
        Result result = Result.ONGOING;
        while (queue.size() > 1) {
            displayGameBase();
            Player currentPlayer = queue.poll();
            result = makeMove(currentPlayer);

            if (result == Result.SAVE) {
                queue.addFirst(currentPlayer);
                saveGame(queue);
            }

            if (result == Result.DRAW) {
                // Game is ended.
                break;
            }

            if (result == Result.ONGOING) {
                // Move didn't end the game for current player, adding back to the queue.
                queue.offer(currentPlayer);
                continue;
            }

            // Game is complete for the current player.
            if (result == Result.WIN || result == Result.LOSE) {
                leaderBoard.add(currentPlayer);
            }
        }
        displayResult(leaderBoard, result);
    }

    private void saveGame(Deque<Player> queue) throws IOException {
        String gameDirectory = ConsoleManager.readLine("Please enter the name under which you would like to save the game: ");
        Path saveDirectoryPath = getSaveDirectoryPath().resolve(gameDirectory);

        // saving player information
        int idx = 1;
        while (queue.size() > 0) {
            String currentPlayerFile = String.format("%s/player%d.ser", saveDirectoryPath, idx++);
            Player currentPlayer = queue.poll();
            Database.storeData(currentPlayer, currentPlayerFile);
        }

        // saving gameBase
        String gameBaseFile = String.format("%s/gameBase.ser", saveDirectoryPath);
        Database.storeData(gameBase, gameBaseFile);
    }

    //============ helpers ================//
    protected void performOrdering() {
        Collections.shuffle(players);
    }

    private String getAbsolutePropertyFilePath() {
        return SRC_DIRECTORY + getRelativePropertyFilePath();
    }

    private Set<String> getFilesInDirectory(Path directoryPath) throws Exception {
        Set<String> files = new HashSet<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath)) {
            for (Path path : directoryStream) {
                files.add(path.getFileName().toString());
            }
        } catch (IOException e) {
            throw new Exception(ExceptionReason.UNABLE_TO_LOAD_GAME.getMessage());
        }
        return files;
    }

    private Path getSaveDirectoryPath() {
        return Paths.get(
            SRC_DIRECTORY,
            gameProperties.getProperty(GameProperties.SAVED_GAMES_LOCATION));
    }

    private void initializeGameBase() {
        this.gameBase = createGameBase();
    }

    private void initializePlayers() {
        int playerCount = getPlayerCount();
        for (int idx = 0; idx < playerCount; idx++) {
            Player player = createPlayer(idx);
            players.add(player);
        }
    }

    protected int getPlayerCount() {
        int playerCount =
            ConsoleManager.getUserInput(
                "Enter the number of Players: ",
                input -> Integer.parseInt(input));
        return playerCount;
    }

    private int getUserInputForGameStartup() {
        String displayMessage = "Please select an option: \n" +
            "Press 0 to start a new game.\n" +
            "Press 1 to load a previous game.\n";

        return ConsoleManager.getUserInput(displayMessage, input -> {
            int userInput = Integer.parseInt(input);
            if (userInput == 0 || userInput == 1) {
                return userInput;
            }
            throw new RuntimeException();
        });
    }

    protected Result makeMove(Player currentPlayer) {
        Result result = currentPlayer.performOperation(gameBase);
        return result;
    }

    //============ abstract ================//
    abstract String getRelativePropertyFilePath();

    abstract GameBase createGameBase();

    abstract Player createPlayer(int playerCount);

    abstract void displayGameBase();

    abstract void displayResult(List<Player> leaderBoard, Result result) throws Exception;
}
