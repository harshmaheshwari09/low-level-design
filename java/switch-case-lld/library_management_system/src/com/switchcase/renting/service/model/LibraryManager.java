package com.switchcase.renting.service.model;

import com.switchcase.renting.service.user.User;
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
                adminService.addItem(book, serviceProperty);
            }
            case REMOVE_BOOK,
                BLOCK_USER,
                UNBLOCK_USER -> {
            }
        }
    }
}
