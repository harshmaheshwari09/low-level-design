package com.switchcase.renting.service.model;

import com.switchcase.games.util.ConsoleManager;

import java.io.IOException;

public class CustomerLogin {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ConsoleManager.print("----------Welcome to CUSTOMER PORTAL----------");
        RentingService rentingService = new LibraryManagement();
        rentingService.startSession(new LibraryCustomer());
    }
}