package com.switchcase.renting.service.model;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.database.user.CustomerDetailsDB;
import com.switchcase.renting.service.database.user.ManagerDetailsDB;
import com.switchcase.renting.service.database.user.UserDetailsDB;
import com.switchcase.renting.service.model.service.AdminService;
import com.switchcase.renting.service.model.service.DisplayService;
import com.switchcase.renting.service.util.CustomRuntimeException;
import com.switchcase.renting.service.util.LibraryOperations;

import java.io.IOException;
import java.util.Properties;

public class LibraryManager extends LibraryUser {
    private static final long serialVersionUID = 124L;

    AdminService adminService;

    @Override
    public UserDetailsDB getUserDB(Properties serviceProperties) throws IOException, ClassNotFoundException {
        return ManagerDetailsDB.getInstance(serviceProperties);
    }

    @Override
    public Operation[] getUserOperations() {
        return LibraryOperations.getManagerOperations();
    }

    @Override
    public String getAuthDatabaseFileName() {
        return "managerAuthDB.ser";
    }

    @Override
    void displayUserProfile(Properties serviceProperty) throws IOException, ClassNotFoundException {
        String userID = getUserId(serviceProperty);
        DisplayService displayService = DisplayService.getInstance();
        displayService.displayUserProfile(serviceProperty, userID);
    }

    @Override
    void returnBook(Properties serviceProperty) throws IOException, ClassNotFoundException {
        String userID = getUserId(serviceProperty);
        returnBook(serviceProperty, userID);
    }

    private String getUserId(Properties serviceProperty) throws IOException, ClassNotFoundException {
        UserDetailsDB userDetailsDB = CustomerDetailsDB.getInstance(serviceProperty);
        String userID = ConsoleManager.getUserInput("Enter the userID: ", input -> {
            if (userDetailsDB.isValidID(input.trim())) {
                return input.trim();
            }
            throw CustomRuntimeException.invalidID();
        });
        return userID;
    }

    @Override
    void issueBook(Properties serviceProperty) throws IOException, ClassNotFoundException {
        String userID = getUserId(serviceProperty);
        issueBook(serviceProperty, userID);
    }

    protected void addBook(Properties serviceProperty) throws IOException, ClassNotFoundException {
        Book book = Book.createNewBook();
        adminService = AdminService.getInstance();
        String bookId = adminService.addItem(book, serviceProperty);
        ConsoleManager.print(String.format("======== BOOK(id# %s) ADDED SUCCESSFULLY ======", bookId));
    }

    protected void removeBook(Properties serviceProperty) {
        String bookId = ConsoleManager.getUserInput("Enter the bookId for removal: ", input -> {
            if (input.length() == 7) {
                return input;
            }
            throw CustomRuntimeException.invalidID();
        });
        adminService = AdminService.getInstance();
        try {
            adminService.removeItem(bookId, serviceProperty);
        } catch (Exception exception) {
            ConsoleManager.print(exception.getMessage());
            return;
        }
        ConsoleManager.print(String.format("======== BOOK(id# %s) REMOVED SUCCESSFULLY ======", bookId));
    }

    protected void blockUser(Properties serviceProperty, Operation operation, boolean shouldBlock) throws IOException, ClassNotFoundException {
        UserDetailsDB userDetailsDB = getUserDB(serviceProperty);
        String userId = ConsoleManager.getUserInput(String.format("Enter the userId to %s: ", operation), input -> {
            if (input.length() != 0 && userDetailsDB.isValidID(input.trim())) {
                return input.trim();
            }
            throw CustomRuntimeException.invalidID();
        });
        adminService = AdminService.getInstance();
        adminService.blockUser(userId, serviceProperty, shouldBlock);
        ConsoleManager.print(String.format("======== USER(id# %s) %s SUCCESSFULLY ======", userId, operation));
    }
}
