package com.switchcase.games.util;

import com.switchcase.games.model.GamePiece;

public enum TicTacToePiece implements GamePiece {
    X("X"),
    O("O"),
    EMPTY(" ");

    String value;

    TicTacToePiece(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
