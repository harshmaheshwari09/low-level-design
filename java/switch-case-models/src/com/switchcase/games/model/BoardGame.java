package com.switchcase.games.model;

public abstract class BoardGame extends GameController {

    protected void validateCoordinates(int[] coordinates) throws Exception {
        int boardSize = gameBase.getGameScale();
        if (isNotInRange(coordinates, boardSize) || gameBase.get(coordinates[0], coordinates[1]) != getDefaultGamePiece()) {
            throw new Exception();
        }
    }

    private boolean isNotInRange(int[] coordinates, int boardSize) {
        return coordinates[0] < 0 || coordinates[0] == boardSize || coordinates[1] < 0 || coordinates[1] == boardSize;
    }
}
