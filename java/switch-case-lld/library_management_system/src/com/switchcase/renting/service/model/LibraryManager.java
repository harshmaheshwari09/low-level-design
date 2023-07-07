package com.switchcase.renting.service.model;

import com.switchcase.renting.service.user.User;
import com.switchcase.renting.service.util.LibraryOperations;
import com.switchcase.renting.service.util.Operation;
import com.switchcase.renting.service.util.ServiceProperty;

import java.util.Properties;

public class LibraryManager extends User {

    @Override
    public Operation[] getUserOperations() {
        return LibraryOperations.getManagerOperations();
    }

    @Override
    public String getDatabaseLocation(Properties serviceProperty) {
        return serviceProperty.getProperty(ServiceProperty.USER_DATABASE_LOCATION) + "/manager/";
    }

    @Override
    public void performOperation(Operation userOperation) {
        System.out.print(userOperation);
    }
}
