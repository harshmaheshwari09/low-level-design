package com.switchcase.games.model;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.games.util.Result;
import com.switchcase.games.util.TicTacToePiece;

import java.util.Arrays;

public class TicTacToePlayer extends BoardGamePlayer {

    public TicTacToePlayer(String name, int playerInventory, GamePiece playerPiece) {
        super(name, playerInventory, playerPiece);
    }

    @Override
    protected boolean isNotSafeToPlace(int[] coordinates, GameBase board) {
        return board.get(coordinates[0], coordinates[1]) != TicTacToePiece.EMPTY;
    }

    @Override
    public Result performOperation(GameBase board) {
        String playerName = getPlayerName();
        String displayMessage =
            String.format(
                "%s, Enter coordinates for next move (space-separated) or 'S' to save game: ",
                playerName.toUpperCase());

        return ConsoleManager.getUserInput(displayMessage, input -> {
            // user wants to save game and leave.
            input = input.toUpperCase();
            if (input.equals("S")) {
                return Result.SAVE;
            }
            int coordinates[] = Arrays.stream(input.split("\\s+")).mapToInt(Integer::parseInt).toArray();
            if (isValidateCoordinates(coordinates, board)) {
                return updateGameBase(coordinates, board);
            }
            throw new RuntimeException();
        });
    }

    private Result updateGameBase(int[] coordinates, GameBase board) {
        board.update(coordinates, this.getPlayerPiece());
        this.updatePlayerInventory(this.getPlayerInventory() - 1);
        return validateVictory(coordinates, this.getPlayerPiece(), board);
    }

    private Result validateVictory(int[] coordinates, GamePiece playerPiece, GameBase board) {
        int row = coordinates[0];
        int col = coordinates[1];
        if (isHorizontalMatch(row, playerPiece, board) || isVerticalMatch(col, playerPiece, board) || isDiagonalMatch(row, col, playerPiece, board) || isAntiDiagonalMatch(row, col, playerPiece, board)) {
            return Result.WIN;
        }
        return Result.ONGOING;
    }

    private boolean isHorizontalMatch(int row, GamePiece playerPiece, GameBase board) {
        int boardSize = board.getGameScale();
        for (int column = 0; column < boardSize; column++) {
            if (board.get(row, column) != playerPiece) {
                return false;
            }
        }
        return true;
    }

    private boolean isVerticalMatch(int col, GamePiece playerPiece, GameBase board) {
        int boardSize = board.getGameScale();
        for (int row = 0; row < boardSize; row++) {
            if (board.get(row, col) != playerPiece) {
                return false;
            }
        }
        return true;
    }

    private boolean isDiagonalMatch(int row, int col, GamePiece playerPiece, GameBase board) {
        if (row != col) {
            return false;
        }

        int boardSize = board.getGameScale();
        for (int i = 0; i < boardSize; i++) {
            if (board.get(i, i) != playerPiece) {
                return false;
            }
        }
        return true;
    }

    private boolean isAntiDiagonalMatch(int row, int col, GamePiece playerPiece, GameBase board) {
        int boardSize = board.getGameScale();
        if (row + col != boardSize) {
            return false;
        }

        for (int i = 0; i < boardSize; i++) {
            if (board.get(i, boardSize - 1 - i) != playerPiece) {
                return false;
            }
        }
        return true;
    }
}
