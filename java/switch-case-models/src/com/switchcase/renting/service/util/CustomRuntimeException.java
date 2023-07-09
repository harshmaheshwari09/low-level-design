package com.switchcase.renting.service.util;

import java.io.IOException;

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

    public static CustomRuntimeException invalidBookID() {
        return new CustomRuntimeException(ExceptionMessage.INVALID_BOOK_ID.getMessage());
    }

    public static CustomRuntimeException invalidUserID() {
        return new CustomRuntimeException(ExceptionMessage.INVALID_USER_ID.getMessage());
    }

    public static CustomRuntimeException userBlocked() {
        return new CustomRuntimeException(ExceptionMessage.USER_BLOCKED.getMessage());
    }

    public static CustomRuntimeException illegalOperation() {
        return new CustomRuntimeException(ExceptionMessage.ILLEGAL_OPERATION.getMessage());
    }

    private enum ExceptionMessage {
        USER_NAME_NOT_FOUND("ERROR! Invalid username\n"),
        USER_NAME_NOT_AVAILABLE("ERROR! Username is not available\n"),
        INVALID_PASSWORD("ERROR! Invalid password\n"),
        UNABLE_TO_LOAD_SERVICE_PROPERTIES("ERROR! Unable to load service properties\n"),
        MISSING_INPUT("ERROR! No input provided\n"),
        INVALID_BOOK_ID("ERROR! Input bookID is invalid\n"),
        INVALID_USER_ID("ERROR! Input userID is invalid\n"),
        USER_BLOCKED("AUTHENTICATION ERROR! User is blocked \n"),
        ILLEGAL_OPERATION("ERROR! User is not authorized to perform this operation.\n");

        String message;

        ExceptionMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }
}
