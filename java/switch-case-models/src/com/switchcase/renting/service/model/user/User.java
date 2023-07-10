package com.switchcase.renting.service.model.user;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.database.user.UserDetailsDB;
import com.switchcase.renting.service.model.Operation;
import com.switchcase.renting.service.util.CustomRuntimeException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

public abstract class User implements Serializable {
    protected String userID;
    protected String name;
    protected String lastName;
    private static final long serialVersionUID = 1L;

    public abstract UserDetailsDB getUserDB(Properties serviceProperties) throws IOException, ClassNotFoundException;

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
        this.userID = user.userID;
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

    public abstract void performOperation(Operation userOperation, Properties serviceProperty) throws IOException, ClassNotFoundException;

    abstract public String getAuthDatabaseFileName();

    public void buildUserID(String userName) {
        this.userID = userName;
    }

    public void printDetails() {
        ConsoleManager.print(String.format("UserID : %s\n", userID));
        ConsoleManager.print(String.format("Name : %s %s\n", name, lastName));
    }
}
