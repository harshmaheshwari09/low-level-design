package com.switchcase.games.model;

public abstract class Board implements GameBase {
    GamePiece[][] board;

    @Override
    public int getGameScale() {
        return board.length;
    }

    @Override
    public GamePiece get(int x, int y) {
        return board[x][y];
    }
}
