package com.switchcase.renting.service.database.user;

import com.switchcase.database.model.Database;
import com.switchcase.renting.service.database.RentingServiceDB;
import com.switchcase.renting.service.model.user.User;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class UserDetailsDB extends RentingServiceDB {


    private Map<String, User> userDetailsDB;

    protected UserDetailsDB(Properties serviceProperty) throws IOException, ClassNotFoundException {
        super(serviceProperty);
    }

    public User loadUser(String userName) {
        return userDetailsDB.get(userName);
    }

    public void registerNewUser(String userName, User user) throws IOException {
        user.buildName();
        user.buildLastName();
        user.buildUserID(userName);
        userDetailsDB.put(userName, user);
        Database.storeData(userDetailsDB, getDbLocation());
    }

    @Override
    protected void loadDB(String filePath) throws IOException, ClassNotFoundException {
        if (!Files.isRegularFile(Paths.get(filePath))) {
            userDetailsDB = new HashMap<>();
        } else {
            userDetailsDB = (Map) Database.loadData(filePath);
        }
    }

    @Override
    protected String getPath() {
        return ServiceProperty.USER_DB_PATH;
    }

    public boolean isValidID(String userID) {
        return userDetailsDB.keySet().contains(userID);
    }

    public User getUser(String userID) {
        return userDetailsDB.get(userID);
    }
}
