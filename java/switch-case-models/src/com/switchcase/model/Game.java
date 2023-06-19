package com.switchcase.model;

import com.switchcase.util.GameConfiguration;

public interface Game {

    void init(GameConfiguration gameConfiguration);

    void initializeConfig(GameConfiguration gameConfiguration);

    void initializePlayers();

    void playGame();
}
