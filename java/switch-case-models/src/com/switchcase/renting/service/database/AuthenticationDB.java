package com.switchcase.renting.service.database;

import com.switchcase.database.model.Database;
import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.util.CustomRuntimeException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// @Singleton
public class AuthenticationDB {

    private static AuthenticationDB obj;
    private Map<String, Map<String, Integer>> credentials;

    private AuthenticationDB() {
    }

    public static AuthenticationDB getInstance() {
        if (obj == null) {
            obj = new AuthenticationDB();
        }
        return obj;
    }

    private void loadDB(String filePath) throws IOException, ClassNotFoundException {
        if (!Files.isRegularFile(Paths.get(filePath))) {
            credentials = new HashMap<>();
        } else {
            credentials = (Map) Database.loadData(filePath);
        }
    }

    public int validateCredentials(String filePath) throws IOException, ClassNotFoundException {
        loadDB(filePath);
        ConsoleManager.print("\nPlease enter your credentials: ");
        String userName = ConsoleManager.getUserInput("\nUsername: ", input -> {
            if (credentials.containsKey(input)) {
                return input;
            }
            throw CustomRuntimeException.userNotFoundException();
        });

        int userId = ConsoleManager.getUserInput("Password: ", input -> {
            if (credentials.get(userName).containsKey(input)) {
                return credentials.get(userName).get(input);
            }
            throw CustomRuntimeException.invalidPasswordException();
        });

        return userId;
    }

    public void registerNewUser(String filePath, int userId) throws IOException, ClassNotFoundException {
        loadDB(filePath);
        ConsoleManager.print("\nPlease submit the details:");
        String userName = ConsoleManager.getUserInput("\nUsername: ", input -> {
            if (!credentials.containsKey(input)) {
                return input;
            }
            throw CustomRuntimeException.userNameNotAvailableException();
        });
        String password = ConsoleManager.getUserInput("Password: ", input -> {
            if (input.length() > 0) {
                return input;
            }
            throw CustomRuntimeException.invalidPasswordException();
        });
        credentials.put(userName, Map.of(password, userId));
        Database.storeData(credentials, filePath);
    }
}
