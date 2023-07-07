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
    private Map<String, String> credentials;

    private AuthenticationDB() {
    }

    public static AuthenticationDB getInstance() {
        if (obj == null) {
            obj = new AuthenticationDB();
        }
        return obj;
    }

    private void loadDB(String filePath) throws IOException, ClassNotFoundException {
        if (credentials != null) {
            return;
        }

        if (!Files.isRegularFile(Paths.get(filePath))) {
            credentials = new HashMap<>();
        } else {
            credentials = (Map) Database.loadData(filePath);
        }
    }

    public String validateCredentials(String filePath) throws IOException, ClassNotFoundException {
        loadDB(filePath);
        ConsoleManager.print("\nPlease enter your credentials: ");
        String userName = ConsoleManager.getUserInput("\nUsername: ", input -> {
            if (credentials.containsKey(input)) {
                return input;
            }
            throw CustomRuntimeException.userNotFoundException();
        });

        return ConsoleManager.getUserInput("Password: ", input -> {
            if (credentials.get(userName).equals(input)) {
                return userName;
            }
            throw CustomRuntimeException.invalidPasswordException();
        });
    }

    public String registerNewUser(String filePath) throws IOException, ClassNotFoundException {
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
        credentials.put(userName, password);
        Database.storeData(credentials, filePath);
        return userName;
    }
}
