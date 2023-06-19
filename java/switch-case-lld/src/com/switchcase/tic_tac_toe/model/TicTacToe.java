package com.switchcase.tic_tac_toe.model;

import com.switchcase.model.BoardGame;
import com.switchcase.util.GameConfiguration;

public abstract class TicTacToe extends BoardGame {
    public TicTacToe() {
        super(GameConfiguration.TWO_PLAYER);
    }
}
