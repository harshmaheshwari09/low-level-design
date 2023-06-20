package com.switchcase.games.model;

public class TicTacToePlayer extends PlayerController {

    public TicTacToePlayer(String name, int playerInventory, GamePiece playerPiece) {
        super(name, playerInventory, playerPiece);
    }

    @Override
    public String performOperation() {
        String playerName = getPlayerName();
        return CONSOLE.readLine(
            String.format(
                "%s, please enter space separated coordinates for the next move: ",
                playerName.toUpperCase()));
    }
}
