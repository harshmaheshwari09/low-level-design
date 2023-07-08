package com.switchcase.renting.service.model;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.database.AuthenticationDB;
import com.switchcase.renting.service.database.UserDB;
import com.switchcase.renting.service.user.User;
import com.switchcase.renting.service.util.AccessType;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.util.Properties;

public class AuthenticationService {

    private final static String USER_INFO_DB_FILENAME = "userInfo.ser";
    private final static String AUTHENTICATION_DB_FILENAME = "authentication.ser";

    private AuthenticationService() {
    }

    public static void execute(User user, Properties serviceProperties) throws IOException, ClassNotFoundException {
        AccessType accessType = getAccessType();
        switch (accessType) {
            case LOG_IN -> {
                AuthenticationDB authenticationDB = AuthenticationDB.getInstance();
                String userName =
                    authenticationDB.validateCredentials(
                        getDataBaseFilePath(user.getDatabaseLocation(serviceProperties), AUTHENTICATION_DB_FILENAME));

                UserDB userDB = UserDB.getInstance();
                user.copy(
                    userDB.loadUser(
                        getDataBaseFilePath(user.getDatabaseLocation(serviceProperties), USER_INFO_DB_FILENAME),
                        userName));
            }
            case REGISTER -> {
                AuthenticationDB authenticationDB = AuthenticationDB.getInstance();
                String userName = authenticationDB.registerNewUser(
                    getDataBaseFilePath(user.getDatabaseLocation(serviceProperties), AUTHENTICATION_DB_FILENAME));

                UserDB userDB = UserDB.getInstance();
                userDB.registerNewUser(
                    getDataBaseFilePath(user.getDatabaseLocation(serviceProperties), USER_INFO_DB_FILENAME),
                    userName, user);
            }
        }
    }

    static String getDataBaseFilePath(String databaseLocation, String fileName) {
        return ServiceProperty.SRC_DIRECTORY + databaseLocation + fileName;
    }

    static AccessType getAccessType() {
        return AccessType.getAccessType(ConsoleManager.getUserInput(
            "\nPress 0 to LOG_IN\nPress 1 to REGISTER\n", input -> {
                int userInput = Integer.parseInt(input);
                if (userInput == 0 || userInput == 1) {
                    return userInput;
                }
                throw new RuntimeException();
            }));
    }
}
