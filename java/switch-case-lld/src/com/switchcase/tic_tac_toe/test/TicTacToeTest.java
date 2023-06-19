package com.switchcase.tic_tac_toe.test;

import com.switchcase.tic_tac_toe.model.MockTicTacToe;
import com.switchcase.tic_tac_toe.model.TicTacToe;
import com.switchcase.util.GameConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicTacToeTest {

    @BeforeEach

    @Test
    public void testCreatingObject() {
        TicTacToe ticTacToe = new MockTicTacToe();
        Assertions.assertNotNull(ticTacToe);
    }

    @Test
    public void testValidatingGameConfiguration() {
        TicTacToe ticTacToe = new MockTicTacToe();
        Assertions.assertEquals(GameConfiguration.TWO_PLAYER, ticTacToe.getGameConfiguration());
    }
}
