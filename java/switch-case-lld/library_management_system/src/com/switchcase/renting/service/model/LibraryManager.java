package com.switchcase.renting.service.model;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.user.User;
import com.switchcase.renting.service.util.CustomRuntimeException;
import com.switchcase.renting.service.util.LibraryOperations;
import com.switchcase.renting.service.util.Operation;

import java.io.IOException;
import java.util.Properties;

public class LibraryManager extends User {

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

    @Override
    public void performOperation(Operation operation, Properties serviceProperty)
        throws IOException, ClassNotFoundException {
        switch ((LibraryOperations) operation) {
            // display service
            case SEARCH_BOOK,
                SHOW_PROFILE -> System.out.println("display service: " + operation);

            // reserve service
            case ISSUE_BOOK,
                RETURN_BOOK,
                RE_ISSUE_BOOK,
                RESERVE_BOOK,
                UN_RESERVE_BOOK -> System.out.println("reserve service: " + operation);

            // admin service
            case ADD_BOOK -> addBook(serviceProperty);
            case REMOVE_BOOK -> removeBook(serviceProperty);
            case BLOCK_USER -> blockUser(serviceProperty, operation, true);
            case UNBLOCK_USER -> blockUser(serviceProperty, operation, false);
        }
    }

    private void addBook(Properties serviceProperty) throws IOException, ClassNotFoundException {
        Book book = Book.createNewBook();
        adminService = AdminService.getInstance();
        String bookId = adminService.addItem(book, serviceProperty);
        ConsoleManager.print(String.format("======== BOOK(id# %s) ADDED SUCCESSFULLY ======", bookId));
    }

    private void removeBook(Properties serviceProperty) {
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

    private void blockUser(Properties serviceProperty, Operation operation, boolean shouldBlock) throws IOException, ClassNotFoundException {
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
