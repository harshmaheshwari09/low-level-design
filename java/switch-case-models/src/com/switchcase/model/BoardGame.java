package com.switchcase.model;

import com.switchcase.util.GameConfiguration;

public abstract class BoardGame extends TableTopGames {
    Board board;

    public BoardGame(GameConfiguration gameConfiguration) {
        init(gameConfiguration);
        initializeBoard();
    }

    protected abstract void initializeBoard();

    protected abstract void displayBoard();
}
