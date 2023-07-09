package com.switchcase.renting.service.login;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.model.LibraryManagement;
import com.switchcase.renting.service.model.LibraryManager;
import com.switchcase.renting.service.model.service.RentingService;

import java.io.IOException;

public class ManagerLogin {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ConsoleManager.print("----------Welcome to MANAGER PORTAL----------");
        RentingService rentingService = new LibraryManagement();
        rentingService.startSession(new LibraryManager());
    }
}
