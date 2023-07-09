package com.switchcase.games.model;

import com.switchcase.games.util.Result;

import java.io.Serializable;

public interface Player extends Serializable {

    Result performOperation(GameBase gameBase);

    String getPlayerName();

    GamePiece getPlayerPiece();

    int getPlayerInventory();

    void updatePlayerInventory(int playerInventory);
}
