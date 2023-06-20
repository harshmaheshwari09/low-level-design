package com.switchcase.games.model;

import com.switchcase.games.util.ExceptionReason;
import com.switchcase.games.util.OperationStatus;
import com.switchcase.games.util.Result;
import com.switchcase.games.util.TicTacToePiece;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TicTacToeGame extends BoardGame {

    @Override
    public GameBase createGameBase() {
        int boardSize = getBoardSize();
        return new TicTacToeBoard(boardSize);
    }

    @Override
    public void displayGameBase() {
        int boardSize = gameBase.getGameScale();
        for (int i = 0; i < boardSize; i++) {
            CONSOLE.print("=".repeat(3*boardSize + 4) + "\n|");
            for (int j = 0; j < boardSize; j++) {
                CONSOLE.print(String.format(" %s |", gameBase.get(i, j).getValue()));
            }
            CONSOLE.print("\n");
        }
        CONSOLE.print("=".repeat(3*boardSize + 4) + "\n");
    }

    private int getBoardSize() {
        boolean shouldTakeInput = true;
        int boardSize = 0;
        while (shouldTakeInput) {
            try {
                boardSize = Integer.parseInt(CONSOLE.readLine("Enter the size of the board: ").trim());
                shouldTakeInput = false;
            } catch (Exception e) {
                CONSOLE.print(ExceptionReason.ILLEGAL_BOARD_SIZE.getMessage());
            }
        }
        return boardSize;
    }

    @Override
    public GamePiece getDefaultGamePiece() {
        return TicTacToePiece.EMPTY;
    }

    @Override
    public int getPlayerCount() {
        return 2;
    }

    @Override
    public Player createPlayer(int playerCount) {
        var playerName = CONSOLE.readLine(String.format("Enter the name of player%d: ", playerCount + 1));
        int boardSize = gameBase.getGameScale();
        int playerInventory = boardSize * boardSize / 2;
        GamePiece playerPiece = TicTacToePiece.O;
        if (playerCount == 0) {
            playerInventory++;
            playerPiece = TicTacToePiece.X;
        }
        return new TicTacToePlayer(playerName, playerInventory, playerPiece);
    }

    @Override
    public void performOrdering() {
        Collections.shuffle(this.players);
    }

    @Override
    public OperationStatus updateGameBase(String instruction, Player currentPlayer) {
        int[] coordinates;
        try {
            coordinates = Arrays.stream(instruction.split("\\s+")).mapToInt(Integer::parseInt).toArray();
            validateCoordinates(coordinates);
        } catch (Exception e) {
            CONSOLE.print(ExceptionReason.INVALID_INPUT_PROVIDED.getMessage());
            return OperationStatus.FAILURE;
        }
        gameBase.update(coordinates, currentPlayer.getPlayerPiece());
        currentPlayer.updatePlayerInventory(currentPlayer.getPlayerInventory() - 1);
        return OperationStatus.SUCCESS;
    }

    @Override
    public Result computeOperationResult(String instruction, Player currentPlayer) {
        int[] coordinates = Arrays.stream(instruction.split("\\s+")).mapToInt(Integer::parseInt).toArray();
        return validateVictory(coordinates, currentPlayer.getPlayerPiece());
    }

    private Result validateVictory(int[] coordinates, GamePiece playerPiece) {
        int row = coordinates[0];
        int col = coordinates[1];
        if( isHorizontalMatch(row, playerPiece) || isVerticalMatch(col, playerPiece)
            || isDiagonalMatch(row, col, playerPiece) || isAntiDiagonalMatch(row, col, playerPiece)) {
            return Result.WIN;
        }
        return Result.ONGOING;
    }


    private boolean isHorizontalMatch(int row, GamePiece playerPiece) {
        int boardSize = gameBase.getGameScale();
        for (int column = 0; column < boardSize; column++) {
            if (gameBase.get(row, column) != playerPiece) {
                return false;
            }
        }
        return true;
    }

    private boolean isVerticalMatch(int col, GamePiece playerPiece) {
        int boardSize = gameBase.getGameScale();
        for (int row = 0; row < boardSize; row++) {
            if (gameBase.get(row, col) != playerPiece) {
                return false;
            }
        }
        return true;
    }

    private boolean isDiagonalMatch(int row, int col, GamePiece playerPiece) {
        if (row != col) {
            return false;
        }

        int boardSize = gameBase.getGameScale();
        for (int i = 0; i < boardSize; i++) {
            if (gameBase.get(i, i) != playerPiece) {
                return false;
            }
        }
        return true;
    }

    private boolean isAntiDiagonalMatch(int row, int col, GamePiece playerPiece) {
        int boardSize = gameBase.getGameScale();
        if (row + col != boardSize) {
            return false;
        }

        for (int i = 0; i < boardSize; i++) {
            if (gameBase.get(i, boardSize - 1 - i) != playerPiece) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void displayResult(List<Player> leaderBoard, Result result) throws Exception {
        String message = switch (result) {
            case DRAW -> "Alas! the game ends on a DRAW!.\n";
            case WIN -> "CONGRATS!!Here is the leaderboard:\n";
            case LOSE -> {
                Collections.reverse(leaderBoard);
                yield "CONGRATS!!\nHere is the leaderboard:\n";
            }
            case ONGOING -> {
                throw new Exception(ExceptionReason.INCORRECT_GAME_RESULT.getMessage());
            }
        };
        CONSOLE.print(message);
        for (int curr = 0; curr < leaderBoard.size(); curr++) {
            CONSOLE.print(
                String.format("%d. %s\n",
                    curr + 1,
                    leaderBoard.get(curr).getPlayerName().toUpperCase()));
        }
    }

    @Override
    public Result makeMove(Player currentPlayer) {
        if (currentPlayer.getPlayerInventory() == 0) {
            return Result.DRAW;
        }
        return super.makeMove(currentPlayer);
    }
}
