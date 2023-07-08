package com.switchcase.renting.service.database;

import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.util.Properties;

public abstract class RentingServiceDB {
    private String dbLocation;

    public String getDbLocation() {
        return dbLocation;
    }

    public RentingServiceDB(Properties serviceProperties) throws IOException, ClassNotFoundException {
        setDatabaseLocation(serviceProperties);
        loadDB(dbLocation);
    }

    protected void setDatabaseLocation(Properties serviceProperty) {
        dbLocation = ServiceProperty.SRC_DIRECTORY
            + serviceProperty.getProperty(ServiceProperty.USER_DATABASE_LOCATION)
            + getPath()
            + getFileName();
    }

    protected abstract void loadDB(String filePath) throws IOException, ClassNotFoundException;

    protected abstract String getPath();

    protected abstract String getFileName();
}
