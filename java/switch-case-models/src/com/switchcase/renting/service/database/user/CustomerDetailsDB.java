package com.switchcase.renting.service.database.user;

import java.io.IOException;
import java.util.Properties;

public class CustomerDetailsDB extends UserDetailsDB {

    private static CustomerDetailsDB obj;

    protected CustomerDetailsDB(Properties serviceProperty) throws IOException, ClassNotFoundException {
        super(serviceProperty);
    }

    @Override
    protected String getFileName() {
        return "customerDetailsDB.ser";
    }

    public static CustomerDetailsDB getInstance(Properties serviceProperty) throws IOException, ClassNotFoundException {
        if (obj == null) {
            obj = new CustomerDetailsDB(serviceProperty);
        }
        return obj;
    }
}
