package com.switchcase.renting.service.database;

import com.switchcase.database.model.Database;
import com.switchcase.renting.service.user.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserDB {

    private static UserDB obj;

    private Map<Integer, User> userDetails;
    private Map<Integer, Set<Integer>> userReservations;
    private Map<Integer, Set<Integer>> userBookings;

    private UserDB() {
    }

    public static UserDB getInstance() {
        if (obj == null) {
            obj = new UserDB();
        }
        return obj;
    }

    public User loadUser(String filePath, int userId) throws IOException, ClassNotFoundException {
        if (userDetails == null) {
            userDetails = (Map) Database.loadData(filePath);
        }
        return userDetails.get(userId);
    }

    public int registerNewUser(String filePath, User user) throws IOException, ClassNotFoundException {
        if (!Files.isRegularFile(Paths.get(filePath))) {
            userDetails = new HashMap<>();
        } else {
            userDetails = (Map) Database.loadData(filePath);
        }

        user.buildName();
        user.buildLastName();
        int userId = userDetails.size();
        userDetails.put(userId, user);
        Database.storeData(userDetails, filePath);
        return userId;
    }
}
