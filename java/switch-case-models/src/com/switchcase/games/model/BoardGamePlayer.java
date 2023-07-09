package com.switchcase.games.model;

public abstract class BoardGamePlayer extends PlayerController {
    public BoardGamePlayer(String name, int playerInventory, GamePiece playerPiece) {
        super(name, playerInventory, playerPiece);
    }

    protected boolean isValidateCoordinates(int[] coordinates, GameBase board) {
        int boardSize = board.getGameScale();
        if (isNotInRange(coordinates, boardSize) || isNotSafeToPlace(coordinates, board)) {
            return false;
        }
        return true;
    }

    abstract protected boolean isNotSafeToPlace(int[] coordinates, GameBase board);

    private boolean isNotInRange(int[] coordinates, int boardSize) {
        return coordinates[0] < 0 || coordinates[0] == boardSize || coordinates[1] < 0 || coordinates[1] == boardSize;
    }
}
