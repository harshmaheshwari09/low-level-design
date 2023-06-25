package com.switchcase.games.model;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.games.util.ExceptionReason;
import com.switchcase.games.util.Result;
import com.switchcase.games.util.TicTacToePiece;

import java.util.Collections;
import java.util.List;

public class TicTacToeGame extends BoardGame {

    private static final String RELATIVE_PROPERTY_FILE_PATH =
        "/tic_tac_toe/src/com/switchcase/games/generic/tic_tac_toe.properties";

    //============ constructors ================//
    public TicTacToeGame() throws Exception {
        super();
    }

    //============ key-operations  ================//
    @Override
    public String getRelativePropertyFilePath() {
        return RELATIVE_PROPERTY_FILE_PATH;
    }

    @Override
    public GameBase createGameBase() {
        int boardSize = getBoardSize();
        return new TicTacToeBoard(boardSize);
    }

    @Override
    public int getPlayerCount() {
        return 2;
    }

    @Override
    public Player createPlayer(int playerCount) {
        var playerName = ConsoleManager.readLine(String.format("Enter the name of player%d: ", playerCount + 1));
        int boardSize = gameBase.getGameScale();
        int playerInventory = boardSize * boardSize / 2;
        GamePiece playerPiece = TicTacToePiece.class.getEnumConstants()[playerCount];
        return new TicTacToePlayer(playerName, playerInventory, playerPiece);
    }

    @Override
    public Result makeMove(Player currentPlayer) {
        if (currentPlayer.getPlayerInventory() == 0) {
            return Result.DRAW;
        }
        return super.makeMove(currentPlayer);
    }

    @Override
    protected void performOrdering() {
        super.performOrdering();
        Player firstPlayer = players.get(0);
        firstPlayer.updatePlayerInventory(firstPlayer.getPlayerInventory() + 1);
    }

    @Override
    public void displayGameBase() {
        int boardSize = gameBase.getGameScale();
        for (int i = 0; i < boardSize; i++) {
            ConsoleManager.print("=".repeat(3 * boardSize + 4) + "\n|");
            for (int j = 0; j < boardSize; j++) {
                ConsoleManager.print(String.format(" %s |", gameBase.get(i, j).getValue()));
            }
            ConsoleManager.print("\n");
        }
        ConsoleManager.print("=".repeat(3 * boardSize + 4) + "\n");
    }

    @Override
    public void displayResult(List<Player> leaderBoard, Result result) throws Exception {
        String message = switch (result) {
            case DRAW -> "Alas! the game ends on a DRAW!.\n";
            case SAVE -> "Game saved.\n";
            case WIN -> "CONGRATS!!Here is the leaderboard:\n";
            case LOSE -> {
                Collections.reverse(leaderBoard);
                yield "CONGRATS!!\nHere is the leaderboard:\n";
            }
            case ONGOING -> throw new Exception(ExceptionReason.INCORRECT_GAME_RESULT.getMessage());
        };
        ConsoleManager.print(message);
        for (int curr = 0; curr < leaderBoard.size(); curr++) {
            ConsoleManager.print(String.format("%d. %s\n", curr + 1, leaderBoard.get(curr).getPlayerName().toUpperCase()));
        }
    }

    //============ helpers ================//
    private int getBoardSize() {
        int boardSize =
            ConsoleManager.getUserInput("Enter the size of the board: ", input -> Integer.parseInt(input));
        return boardSize;
    }
}
