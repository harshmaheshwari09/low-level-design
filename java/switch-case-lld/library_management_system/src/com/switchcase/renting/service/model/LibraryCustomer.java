package com.switchcase.renting.service.model;

import com.switchcase.renting.service.util.CustomRuntimeException;
import com.switchcase.renting.service.util.LibraryOperations;

import java.io.IOException;
import java.util.Properties;

public class LibraryCustomer extends LibraryUser {

    @Override
    public Operation[] getUserOperations() {
        return LibraryOperations.getUserOperations();
    }

    @Override
    void addBook(Properties serviceProperty) {
        throw CustomRuntimeException.illegalOperation();
    }

    @Override
    void removeBook(Properties serviceProperty) {
        throw CustomRuntimeException.illegalOperation();
    }

    @Override
    void blockUser(Properties serviceProperty, Operation operation, boolean shouldBlock) throws IOException, ClassNotFoundException {
        throw CustomRuntimeException.illegalOperation();
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
