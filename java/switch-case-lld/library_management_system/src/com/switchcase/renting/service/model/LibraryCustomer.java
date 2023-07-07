package com.switchcase.renting.service.model;

import com.switchcase.renting.service.user.User;
import com.switchcase.renting.service.util.LibraryOperations;
import com.switchcase.renting.service.util.Operation;
import com.switchcase.renting.service.util.ServiceProperty;

import java.util.Properties;

public class LibraryCustomer extends User {

    @Override
    public Operation[] getUserOperations() {
        return LibraryOperations.getUserOperations();
    }

    @Override
    public String getDatabaseLocation(Properties serviceProperty) {
        return serviceProperty.getProperty(ServiceProperty.USER_DATABASE_LOCATION) + "/customer/";
    }

    @Override
    public void performOperation(Operation userOperation, Properties serviceProperty) {
        System.out.print(userOperation);
    }
}
