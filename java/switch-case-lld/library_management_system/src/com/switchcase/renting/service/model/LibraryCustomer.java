package com.switchcase.renting.service.model;

import com.switchcase.renting.service.user.User;
import com.switchcase.renting.service.util.LibraryOperations;
import com.switchcase.renting.service.util.Operation;

import java.util.Properties;

public class LibraryCustomer extends User {

    @Override
    public Operation[] getUserOperations() {
        return LibraryOperations.getUserOperations();
    }

    @Override
    public void performOperation(Operation userOperation, Properties serviceProperty) {
        System.out.print(userOperation);
    }

    @Override
    public String getAuthDatabaseFileName() {
        return "customerAuthDB.ser";
    }

    @Override
    public String getUserDetailsDatabaseFileName() {
        return "customerDetailsDB.ser";
    }
}
