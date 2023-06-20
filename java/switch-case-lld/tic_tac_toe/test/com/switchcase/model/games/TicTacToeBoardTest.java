package com.switchcase.model.games;

import com.switchcase.games.model.TicTacToeBoard;
import com.switchcase.games.util.TicTacToePiece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TicTacToeBoardTest {

    TicTacToeBoard board = new TicTacToeBoard(3);

    @Test
    public void testCreatingBoard() {
        Assertions.assertNotNull(board);
        int boardSize = board.getGameScale();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Assertions.assertEquals(TicTacToePiece.EMPTY, board.get(i, j));
            }
        }
    }

    @Test
    public void testUpdatingBoard() {
        Assertions.assertEquals(TicTacToePiece.EMPTY, board.get(0, 1));
        board.update(new int[]{0, 1}, TicTacToePiece.X);
        Assertions.assertEquals(TicTacToePiece.X, board.get(0, 1));
    }

    @Test
    public void testGetGameScale() {
        Assertions.assertEquals(3, board.getGameScale());
    }

}
