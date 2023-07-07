package com.switchcase.renting.service.model;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.user.User;
import com.switchcase.renting.service.util.Operation;

public class OperationService {

    private OperationService() {
    }

    public static void execute(User user) {
        do {
            Operation userOperation = user.getOperation();
            user.performOperation(userOperation);
        } while (getTerminationCondition());
    }

    private static boolean getTerminationCondition() {
        return ConsoleManager
            .readLine("\nPress \"y\" to perform another operation, any other key to exit: ")
            .toLowerCase()
            .equals("y");
    }
}
