package com.switchcase.games.model;

import com.switchcase.games.util.OperationStatus;
import com.switchcase.games.util.Result;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class GameController implements Game {
    GameBase gameBase;
    List<Player> players;

    public GameBase getGameBase() {
        return gameBase;
    }

    public List<Player> getPlayers() {
        return players;
    }


    public GameController() {
        initializeGame();
    }

    @Override
    public void initializeGame() {
        initializeGameBase();
        initializePlayers();
        performOrdering();
    }

    private void initializeGameBase() {
        this.gameBase = createGameBase();
    }

    private void initializePlayers() {
        players = new ArrayList<>();
        int playerCount = getPlayerCount();
        for (int idx = 0; idx < playerCount; idx++) {
            Player player = createPlayer(idx);
            players.add(player);
        }
    }

    @Override
    public void startGame() throws Exception {
        Queue<Player> queue = new LinkedList<>(players);
        List<Player> leaderBoard = new ArrayList<>();
        Result result = Result.ONGOING;
        while (queue.size() > 1) {
            Player currentPlayer = queue.poll();
            result = makeMove(currentPlayer);

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

    @Override
    public Result makeMove(Player currentPlayer) {
        OperationStatus operationStatus;
        String instruction;
        do {
            instruction = currentPlayer.performOperation();
            operationStatus = updateGameBase(instruction, currentPlayer);
        } while (operationStatus != OperationStatus.SUCCESS);
        displayGameBase();
        return computeOperationResult(instruction, currentPlayer);
    }
}
