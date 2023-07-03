package com.switchcase.renting.service.user;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.util.CustomRuntimeException;

import java.io.Serializable;

public abstract class User implements Serializable {
    protected String name;
    protected String lastName;

    public void buildName() {
        this.name = ConsoleManager.getUserInput("Name: ", input -> {
            if (input.length() > 0) {
                return input;
            }
            throw CustomRuntimeException.missingInput();
        });
    }

    public void buildLastName() {
        this.lastName = ConsoleManager.getUserInput("LastName: ", input -> {
            if (input.length() > 0) {
                return input;
            }
            throw CustomRuntimeException.missingInput();
        });
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[").append(name).append(" ").append(lastName).append("]");
        return builder.toString();
    }

    public void copy(User user) {
        this.name = user.name;
        this.lastName = user.lastName;
    }
}
