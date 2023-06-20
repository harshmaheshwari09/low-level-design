package com.switchcase.games.util;

import java.util.Scanner;

public class ConsoleManager {

    Scanner scanner = new Scanner(System.in);

    public void print(String message) {
        System.out.print(message);
    }

    public String readLine(String message) {
        print(message);
        return scanner.nextLine().trim();
    }
}
