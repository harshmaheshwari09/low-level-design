package com.switchcase.renting.service.model;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.database.user.AuthenticationDB;
import com.switchcase.renting.service.database.user.BlockedUserDB;
import com.switchcase.renting.service.database.user.UserDetailsDB;
import com.switchcase.renting.service.user.User;
import com.switchcase.renting.service.util.AccessType;
import com.switchcase.renting.service.util.CustomRuntimeException;

import java.io.IOException;
import java.util.Properties;

public class AuthenticationService {

    private AuthenticationService() {
    }

    public static void execute(User user, Properties serviceProperties) throws IOException, ClassNotFoundException {
        AccessType accessType = getAccessType();
        switch (accessType) {
            case LOG_IN -> {
                // authenticate credentials
                AuthenticationDB authenticationDB = AuthenticationDB.getInstance(serviceProperties, user);
                String userName = authenticationDB.validateCredentials();

                // check status (BLOCK / UNBLOCK)
                BlockedUserDB blockedUserDB = BlockedUserDB.getInstance(serviceProperties);
                if (blockedUserDB.isBlocked(userName)) {
                    throw CustomRuntimeException.userBlocked();
                }

                UserDetailsDB userDB = UserDetailsDB.getInstance(serviceProperties, user);
                user.copy(userDB.loadUser(userName));
            }
            case REGISTER -> {
                AuthenticationDB authenticationDB = AuthenticationDB.getInstance(serviceProperties, user);
                String userName = authenticationDB.registerNewUser();

                UserDetailsDB userDB = UserDetailsDB.getInstance(serviceProperties, user);
                userDB.registerNewUser(userName, user);
            }
        }
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
