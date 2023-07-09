package com.switchcase.renting.service.login;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.model.LibraryCustomer;
import com.switchcase.renting.service.model.LibraryManagement;
import com.switchcase.renting.service.model.service.RentingService;

import java.io.IOException;

public class CustomerLogin {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ConsoleManager.print("----------Welcome to CUSTOMER PORTAL----------");
        RentingService rentingService = new LibraryManagement();
        rentingService.startSession(new LibraryCustomer());
    }
}
