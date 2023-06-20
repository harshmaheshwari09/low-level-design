package com.switchcase.games.model;

import com.switchcase.games.util.TicTacToePiece;

public class TicTacToeBoard extends Board {
    public TicTacToeBoard(int boardSize) {
        board = new TicTacToePiece[boardSize][boardSize];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = TicTacToePiece.EMPTY;
            }
        }
    }

    @Override
    public void update(int[] coordinates, GamePiece playerPiece) {
        board[coordinates[0]][coordinates[1]] = playerPiece;
    }
}
