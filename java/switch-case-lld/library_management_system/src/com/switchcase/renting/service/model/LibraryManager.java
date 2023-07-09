package com.switchcase.renting.service.model;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.util.CustomRuntimeException;
import com.switchcase.renting.service.util.LibraryOperations;
import com.switchcase.renting.service.util.Operation;

import java.io.IOException;
import java.util.Properties;

public class LibraryManager extends LibraryUser {

    AdminService adminService;

    @Override
    public Operation[] getUserOperations() {
        return LibraryOperations.getManagerOperations();
    }

    @Override
    public String getAuthDatabaseFileName() {
        return "managerAuthDB.ser";
    }

    @Override
    public String getUserDetailsDatabaseFileName() {
        return "managerDetailsDB.ser";
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
            throw CustomRuntimeException.invalidBookID();
        });
        adminService = AdminService.getInstance();
        try {
            adminService.removeItem(bookId, serviceProperty);
            ConsoleManager.print(String.format("======== BOOK(id# %s) REMOVED SUCCESSFULLY ======", bookId));
        } catch (Exception exception) {
            ConsoleManager.print(exception.getMessage());
        }
    }

    protected void blockUser(Properties serviceProperty, Operation operation, boolean shouldBlock) throws IOException, ClassNotFoundException {
        String userId = ConsoleManager.getUserInput(String.format("Enter the userId to %s: ", operation), input -> {
            if (input.length() != 0) {
                return input;
            }
            throw CustomRuntimeException.invalidUserID();
        });
        adminService = AdminService.getInstance();
        adminService.blockUser(userId, serviceProperty, shouldBlock);
        ConsoleManager.print(String.format("======== USER(id# %s) %s SUCCESSFULLY ======", userId, operation));
    }
}
