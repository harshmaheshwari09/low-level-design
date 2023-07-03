package com.switchcase.renting.service.util;

public class CustomRuntimeException extends RuntimeException {
    public CustomRuntimeException(String message) {
        super(message);
    }

    public static CustomRuntimeException userNotFoundException() {
        return new CustomRuntimeException(ExceptionMessage.USER_NAME_NOT_FOUND.getMessage());
    }

    public static CustomRuntimeException userNameNotAvailableException() {
        return new CustomRuntimeException(ExceptionMessage.USER_NAME_NOT_AVAILABLE.getMessage());
    }

    public static CustomRuntimeException invalidPasswordException() {
        return new CustomRuntimeException(ExceptionMessage.INVALID_PASSWORD.getMessage());
    }

    public static CustomRuntimeException unableToLoadServiceProperties() {
        return new CustomRuntimeException(ExceptionMessage.UNABLE_TO_LOAD_SERVICE_PROPERTIES.getMessage());
    }

    public static CustomRuntimeException missingInput() {
        return new CustomRuntimeException(ExceptionMessage.MISSING_INPUT.getMessage());
    }

    private enum ExceptionMessage {
        USER_NAME_NOT_FOUND("ERROR! Invalid username\n"),
        USER_NAME_NOT_AVAILABLE("ERROR! Username is not available\n"),
        INVALID_PASSWORD("ERROR! Invalid password\n"),
        UNABLE_TO_LOAD_SERVICE_PROPERTIES("ERROR! Unable to load service properties\n"),
        MISSING_INPUT("ERROR! No input provided\n");

        String message;

        ExceptionMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }
}
