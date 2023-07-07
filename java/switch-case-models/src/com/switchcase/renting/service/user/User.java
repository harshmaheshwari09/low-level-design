package com.switchcase.renting.service.user;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.util.CustomRuntimeException;
import com.switchcase.renting.service.util.Operation;

import java.io.Serializable;
import java.util.Properties;

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

    public Operation getOperation() {
        Operation[] operations = getUserOperations();
        ConsoleManager.print("\nPlease select an operation to perform: ");
        for (int idx = 0; idx < operations.length; idx++) {
            ConsoleManager.print(String.format("\nPress %d for %s", idx, operations[idx]));
        }
        return ConsoleManager.getUserInput("\n", input -> {
            int userInput = Integer.parseInt(input);
            if (0 <= userInput && userInput < operations.length) {
                return operations[userInput];
            }
            throw new RuntimeException();
        });
    }

    public abstract Operation[] getUserOperations();

    public abstract String getDatabaseLocation(Properties serviceProperty);

    public abstract void performOperation(Operation userOperation);
}
