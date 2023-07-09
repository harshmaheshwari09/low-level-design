package com.switchcase.renting.service.model.service;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.model.user.User;
import com.switchcase.renting.service.model.Operation;

import java.io.IOException;
import java.util.Properties;

public class OperationService {

    private OperationService() {
    }

    public static void execute(User user, Properties serviceProperty) throws IOException, ClassNotFoundException {
        do {
            Operation userOperation = user.getOperation();
            user.performOperation(userOperation, serviceProperty);
        } while (getTerminationCondition());
    }

    private static boolean getTerminationCondition() {
        return ConsoleManager
            .readLine("\nPress \"y\" to perform another operation, any other key to exit: ")
            .toLowerCase()
            .equals("y");
    }
}
