package com.switchcase.games.util;

public enum ExceptionReason {
    INCORRECT_GAME_RESULT("Game result can't be ONGOING.\n"),
    ILLEGAL_BOARD_SIZE("Board size is not an Integer. TRY AGAIN!\n"),
    INVALID_INPUT_PROVIDED("Invalid input provided. Please TRY AGAIN!\n");

    String message;

    ExceptionReason(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
