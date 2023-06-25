package com.switchcase.games.model;

public interface Game {

    String ROOT_DIRECTORY = System.getProperty("user.dir");
    String SRC_DIRECTORY = ROOT_DIRECTORY + "/java/switch-case-lld";

    void startGame() throws Exception;
}
