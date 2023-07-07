package com.switchcase.renting.service.model;

import com.switchcase.games.util.ConsoleManager;

import java.io.IOException;

public class ManagerLogin {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ConsoleManager.print("----------Welcome to MANAGER PORTAL----------");
        RentingService rentingService = new LibraryManagement();
        rentingService.startSession(new LibraryManager());
    }
}
