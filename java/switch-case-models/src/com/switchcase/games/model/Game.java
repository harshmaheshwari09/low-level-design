package com.switchcase.games.model;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.games.util.OperationStatus;
import com.switchcase.games.util.Result;

import java.util.List;

public interface Game {
    ConsoleManager CONSOLE = new ConsoleManager();

    //============ initializations ================//
    void initializeGame();

    //============ game-actions ================//
    void performOrdering();
    void startGame() throws Exception;
    Result makeMove(Player currentPlayer);
    void displayResult(List<Player> leaderBoard, Result result) throws Exception;


    //============ helper ================//
    Player createPlayer(int playerCount);
    GameBase createGameBase();
    void displayGameBase();
    OperationStatus updateGameBase(String instruction, Player currentPlayer);
    Result computeOperationResult(String instruction, Player currentPlayer);
    GamePiece getDefaultGamePiece();
    int getPlayerCount();

}
