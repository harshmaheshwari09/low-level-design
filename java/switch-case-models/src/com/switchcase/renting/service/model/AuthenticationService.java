package com.switchcase.renting.service.model;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.database.AuthenticationDB;
import com.switchcase.renting.service.database.UserDB;
import com.switchcase.renting.service.user.User;
import com.switchcase.renting.service.util.AccessType;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.util.Properties;

public abstract class AuthenticationService {

    public static int execute(User user, Properties serviceProperties) throws IOException, ClassNotFoundException {
        AccessType accessType = getAccessType();
        Integer userId = null;
        switch (accessType) {
            case LOG_IN -> {
                AuthenticationDB authenticationDB = AuthenticationDB.getInstance();
                userId = authenticationDB.validateCredentials(getDataBaseFilePath(serviceProperties, ServiceProperty.AUTHENTICATION_DB));

                UserDB userDB = UserDB.getInstance();
                user.copy(userDB.loadUser(getDataBaseFilePath(serviceProperties, ServiceProperty.USER_INFO_DB), userId));
            }
            case REGISTER -> {
                UserDB userDB = UserDB.getInstance();
                userId = userDB.registerNewUser(getDataBaseFilePath(serviceProperties, ServiceProperty.USER_INFO_DB), user);

                AuthenticationDB authenticationDB = AuthenticationDB.getInstance();
                authenticationDB.registerNewUser(getDataBaseFilePath(serviceProperties, ServiceProperty.AUTHENTICATION_DB), userId);
            }
        }
        return userId;
    }

    static String getDataBaseFilePath(Properties serviceProperty, String propertyType) {
        return ServiceProperty.SRC_DIRECTORY + serviceProperty.getProperty(propertyType);
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
