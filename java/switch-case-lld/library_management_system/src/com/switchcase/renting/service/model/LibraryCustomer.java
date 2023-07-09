package com.switchcase.renting.service.model;

import com.switchcase.renting.service.database.user.CustomerDetailsDB;
import com.switchcase.renting.service.database.user.UserDetailsDB;
import com.switchcase.renting.service.util.CustomRuntimeException;
import com.switchcase.renting.service.util.LibraryOperations;

import java.io.IOException;
import java.util.Properties;

public class LibraryCustomer extends LibraryUser {

    @Override
    public UserDetailsDB getUserDB(Properties serviceProperties) throws IOException, ClassNotFoundException {
        return CustomerDetailsDB.getInstance(serviceProperties);
    }

    @Override
    public Operation[] getUserOperations() {
        return LibraryOperations.getUserOperations();
    }

    @Override
    void issueBook(Properties serviceProperty) throws IOException, ClassNotFoundException {
        issueBook(serviceProperty, this.userID);
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
}
