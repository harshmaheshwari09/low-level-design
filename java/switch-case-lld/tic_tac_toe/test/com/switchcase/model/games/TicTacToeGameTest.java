package com.switchcase.model.games;

import com.switchcase.games.model.*;
import com.switchcase.games.util.TicTacToePiece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TicTacToeGameTest  {

    TicTacToeGame game = new MockTicTacToeGame();

    @Test
    public void testGameCreation() {
        Assertions.assertNotNull(game);
    }

    @Test
    public void testGameBase() {
        GameBase board = game.getGameBase();

        int boardSize = board.getGameScale();
        Assertions.assertEquals(3, boardSize);

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Assertions.assertEquals(TicTacToePiece.EMPTY, board.get(i, j));
            }
        }
    }

    @Test
    public void testGamePlayers() {
        List<Player> players = game.getPlayers();
        Assertions.assertEquals(2, players.size());

        Set<String> playerNames = players.stream().map(Player::getPlayerName).collect(Collectors.toSet());
        Assertions.assertEquals(Set.of(MockTicTacToeGame.PLAYER_NAME_1, MockTicTacToeGame.PLAYER_NAME_2), playerNames);

        Set<GamePiece> playerPieces = players.stream().map(Player::getPlayerPiece).collect(Collectors.toSet());
        Assertions.assertEquals(Set.of(TicTacToePiece.X, TicTacToePiece.O), playerPieces);


        Set<Integer> playerInventory = players.stream().map(Player::getPlayerInventory).collect(Collectors.toSet());
        Assertions.assertEquals(Set.of(5, 4), playerInventory);
    }
}
