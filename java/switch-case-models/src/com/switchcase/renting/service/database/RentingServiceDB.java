package com.switchcase.renting.service.database;

import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

public abstract class RentingServiceDB {
    private String dbLocation;
    private static final Random random = new Random();
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

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

    // generate a valid 7 digit unique ID
    protected String generateNewID(Set<String> database) {
        String itemId;
        do {
            StringBuilder builder = new StringBuilder(7);
            for (int i = 0; i < 7; i++) {
                int randomIdx = random.nextInt(CHARACTERS.length());
                builder.append(CHARACTERS.charAt(randomIdx));
            }
            itemId = builder.toString();
        } while (database.contains(itemId));
        return itemId;
    }

    protected abstract void loadDB(String filePath) throws IOException, ClassNotFoundException;

    protected abstract String getPath();

    protected abstract String getFileName();
}
