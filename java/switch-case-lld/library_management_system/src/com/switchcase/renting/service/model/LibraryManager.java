package com.switchcase.renting.service.model;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.user.User;
import com.switchcase.renting.service.util.CustomRuntimeException;
import com.switchcase.renting.service.util.LibraryOperations;
import com.switchcase.renting.service.util.Operation;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.util.Properties;

public class LibraryManager extends User {

    AdminService adminService;

    @Override
    public Operation[] getUserOperations() {
        return LibraryOperations.getManagerOperations();
    }

    @Override
    public String getDatabaseLocation(Properties serviceProperty) {
        return serviceProperty.getProperty(ServiceProperty.USER_DATABASE_LOCATION) + "/manager/";
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
            case ADD_BOOK -> {
                Book book = Book.createNewBook();
                adminService = AdminService.getInstance();
                String bookId = adminService.addItem(book, serviceProperty);
                ConsoleManager.print(String.format("======== BOOK(id# %s) ADDED SUCCESSFULLY ======", bookId));
            }
            case REMOVE_BOOK -> {
                String bookId = ConsoleManager.getUserInput("Enter the bookId for removal: ", input -> {
                    if (input.length() == 7) {
                        return input;
                    }
                    throw CustomRuntimeException.invalidBookId();
                });
                adminService = AdminService.getInstance();
                try {

                    adminService.removeItem(bookId, serviceProperty);
                    ConsoleManager.print(String.format("======== BOOK(id# %s) REMOVED SUCCESSFULLY ======", bookId));
                } catch (Exception exception) {
                    ConsoleManager.print(exception.getMessage());
                }
            }
            case BLOCK_USER,
                UNBLOCK_USER -> {
            }
        }
    }
}
