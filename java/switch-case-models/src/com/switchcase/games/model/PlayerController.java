package com.switchcase.games.model;

import java.util.Objects;

public abstract class PlayerController implements Player {

    String playerName;
    int playerInventory;
    GamePiece playerPiece;

    public int getPlayerInventory() {
        return playerInventory;
    }

    public void updatePlayerInventory(int playerInventory) {
        this.playerInventory = playerInventory;
    }

    public GamePiece getPlayerPiece() {
        return playerPiece;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerController that = (PlayerController) o;

        return playerInventory == that.playerInventory
            && Objects.equals(playerName, that.playerName)
            && Objects.equals(playerPiece, that.playerPiece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, playerInventory, playerPiece);
    }

    public PlayerController(String name, int playerInventory, GamePiece playerPiece) {
        this.playerName = name;
        this.playerInventory = playerInventory;
        this.playerPiece = playerPiece;
    }
}
