package com.switchcase.model.games;

import com.switchcase.games.model.TicTacToeBoard;
import com.switchcase.games.model.TicTacToePlayer;
import com.switchcase.games.util.TicTacToePiece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TicTacToePlayerTest {

    private final String PLAYER_NAME = "Player1";
    TicTacToePlayer player = new TicTacToePlayer(PLAYER_NAME, 5, TicTacToePiece.X);

    @Test
    public void testCreatingPlayer() {
        Assertions.assertNotNull(player);
    }

    @Test
    public void testSetterGetterMethods() {
        Assertions.assertEquals(5, player.getPlayerInventory());
        Assertions.assertEquals(PLAYER_NAME, player.getPlayerName());
        Assertions.assertEquals(TicTacToePiece.X, player.getPlayerPiece());

        player.updatePlayerInventory(3);
        Assertions.assertEquals(3, player.getPlayerInventory());
    }

    @Test
    public void testPerformOperation() {
        InputStream systemIn = System.in;
        String input = "0 0";
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());;
        System.setIn(testIn);
        Assertions.assertEquals(input, player.performOperation(new TicTacToeBoard(3)));
        System.setIn(systemIn);
    }
}
