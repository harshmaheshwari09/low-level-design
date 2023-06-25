package com.switchcase.games.util;

import java.util.Scanner;
import java.util.function.Function;

public class ConsoleManager {

    private static final Scanner scanner = new Scanner(System.in);

    public static void print(String message) {
        System.out.print(message);
    }

    public static String readLine(String message) {
        print(message);
        return scanner.nextLine().trim();
    }

    public static <T> T getUserInput(String message, Function<String, T> function) {
        OperationStatus operationStatus;
        do {
            try {
                String input = readLine(message);
                return function.apply(input);
            } catch (Exception exception) {
                print(ExceptionReason.INVALID_INPUT_PROVIDED.getMessage());
                operationStatus = OperationStatus.FAILURE;
            }
        } while (operationStatus != OperationStatus.SUCCESS);
        return null;
    }
}
