package com.switchcase.games.model;

import java.io.Serializable;

public interface GameBase extends Serializable {
    int getGameScale();

    GamePiece get(int x, int y);

    void update(int[] coordinates, GamePiece playerPiece);
}
