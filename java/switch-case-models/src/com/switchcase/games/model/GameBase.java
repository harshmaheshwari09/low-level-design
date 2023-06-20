package com.switchcase.games.model;

public interface GameBase {
    int getGameScale();

    GamePiece get(int x, int y);

    void update(int[] coordinates, GamePiece playerPiece);
}
