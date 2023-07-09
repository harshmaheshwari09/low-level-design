package com.switchcase.games.model;

import com.switchcase.games.util.TicTacToePiece;

public class MockTicTacToeGame extends TicTacToeGame {

    public static final String PLAYER_NAME_1 = "Player1";
    public static final String PLAYER_NAME_2 = "Player2";

    public MockTicTacToeGame() throws Exception {
    }

    @Override
    public GameBase createGameBase() {
        return new TicTacToeBoard(3);
    }

    @Override
    public Player createPlayer(int idx) {
        switch (idx) {
            case 0 -> {
                return new TicTacToePlayer(PLAYER_NAME_1, 5, TicTacToePiece.X);
            }
            case 1 -> {
                return new TicTacToePlayer(PLAYER_NAME_2, 4, TicTacToePiece.O);
            }
        }
        return null;
    }

}
