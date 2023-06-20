package com.switchcase.games.model;

import com.switchcase.games.util.ConsoleManager;

import java.io.Console;

public interface Player {
    ConsoleManager CONSOLE = new ConsoleManager();

    String performOperation();
    String getPlayerName();
    GamePiece getPlayerPiece();
    int getPlayerInventory();
    void updatePlayerInventory(int playerInventory);
}
