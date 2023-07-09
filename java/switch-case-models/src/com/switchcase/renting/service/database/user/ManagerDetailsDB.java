package com.switchcase.renting.service.database.user;

import java.io.IOException;
import java.util.Properties;

public class ManagerDetailsDB extends UserDetailsDB {

    private static ManagerDetailsDB obj;

    protected ManagerDetailsDB(Properties serviceProperty) throws IOException, ClassNotFoundException {
        super(serviceProperty);
    }

    @Override
    protected String getFileName() {
        return "managerDetailsDB.ser";
    }

    public static ManagerDetailsDB getInstance(Properties serviceProperty) throws IOException, ClassNotFoundException {
        if (obj == null) {
            obj = new ManagerDetailsDB(serviceProperty);
        }
        return obj;
    }
}
