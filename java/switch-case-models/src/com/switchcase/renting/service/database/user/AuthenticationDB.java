package com.switchcase.renting.service.database.user;

import com.switchcase.database.model.Database;
import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.database.RentingServiceDB;
import com.switchcase.renting.service.user.User;
import com.switchcase.renting.service.util.CustomRuntimeException;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// @Singleton
public class AuthenticationDB extends RentingServiceDB {

    private static AuthenticationDB obj;
    private Map<String, String> credentials;
    private static User user;

    private AuthenticationDB(Properties serviceProperty) throws IOException, ClassNotFoundException {
        super(serviceProperty);
    }

    public static AuthenticationDB getInstance(Properties serviceProperty, User currentUser) throws IOException, ClassNotFoundException {
        if (obj == null) {
            user = currentUser;
            obj = new AuthenticationDB(serviceProperty);
        }
        return obj;
    }

    @Override
    public void loadDB(String filePath) throws IOException, ClassNotFoundException {
        if (credentials != null) {
            return;
        }

        if (!Files.isRegularFile(Paths.get(filePath))) {
            credentials = new HashMap<>();
        } else {
            credentials = (Map) Database.loadData(filePath);
        }
    }

    @Override
    protected String getPath() {
        return ServiceProperty.USER_DB_PATH;
    }

    @Override
    protected String getFileName() {
        return user.getAuthDatabaseFileName();
    }

    public String validateCredentials() {
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

    public String registerNewUser() throws IOException {
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
        Database.storeData(credentials, getDbLocation());
        return userName;
    }
}
