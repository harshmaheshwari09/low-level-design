package com.switchcase.renting.service.database;

import com.switchcase.database.model.Database;
import com.switchcase.renting.service.user.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class UserDB {

    private static UserDB obj;

    private Map<String, User> userDetails;

    private UserDB() {
    }

    public static UserDB getInstance() {
        if (obj == null) {
            obj = new UserDB();
        }
        return obj;
    }

    private void loadUserDetailsDB(String filePath) throws IOException, ClassNotFoundException {
        if (!Files.isRegularFile(Paths.get(filePath))) {
            userDetails = new HashMap<>();
        } else {
            userDetails = (Map) Database.loadData(filePath);
        }
    }

    public User loadUser(String filePath, String userName) throws IOException, ClassNotFoundException {
        this.loadUserDetailsDB(filePath);
        return userDetails.get(userName);
    }

    public void registerNewUser(String filePath, String userName, User user) throws IOException, ClassNotFoundException {
        this.loadUserDetailsDB(filePath);
        user.buildName();
        user.buildLastName();
        userDetails.put(userName, user);
        Database.storeData(userDetails, filePath);
    }
}
