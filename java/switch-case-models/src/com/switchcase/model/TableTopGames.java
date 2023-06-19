package com.switchcase.model;

import com.switchcase.util.GameConfiguration;

import java.util.List;

public abstract class TableTopGames implements Game {

    List<Player> players;

    public GameConfiguration getGameConfiguration() {
        return gameConfiguration;
    }

    GameConfiguration gameConfiguration;

    @Override
    public void init(GameConfiguration gameConfiguration) {
        initializeConfig(gameConfiguration);
        initializePlayers();
    }

    @Override
    public void initializeConfig(GameConfiguration gameConfiguration) {
        this.gameConfiguration = gameConfiguration;
    }
}
