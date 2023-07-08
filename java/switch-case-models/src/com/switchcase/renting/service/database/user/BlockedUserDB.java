package com.switchcase.renting.service.database.user;

import com.switchcase.database.model.Database;
import com.switchcase.renting.service.database.RentingServiceDB;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BlockedUserDB extends RentingServiceDB {

    private static BlockedUserDB obj;
    private Set<String> blockedUsersDB;

    private BlockedUserDB(Properties serviceProperties) throws IOException, ClassNotFoundException {
        super(serviceProperties);
    }

    public static BlockedUserDB getInstance(Properties serviceProperty) throws IOException, ClassNotFoundException {
        if (obj == null) {
            obj = new BlockedUserDB(serviceProperty);
        }
        return obj;
    }

    @Override
    public void loadDB(String filePath) throws IOException, ClassNotFoundException {
        if (blockedUsersDB != null) {
            return;
        }
        if (!Files.isRegularFile(Paths.get(filePath))) {
            blockedUsersDB = new HashSet<>();
        } else {
            blockedUsersDB = (Set) Database.loadData(filePath);
        }
    }

    @Override
    public String getPath() {
        return ServiceProperty.USER_DB_PATH;
    }

    @Override
    public String getFileName() {
        return "blockedUser.ser";
    }

    public void blockUser(String userID, boolean shouldBlock) throws IOException {
        if (shouldBlock) {
            blockedUsersDB.add(userID);
        } else {
            blockedUsersDB.remove(userID);
        }
        Database.storeData(blockedUsersDB, getDbLocation());
    }

    public boolean isBlocked(String userID) {
        return blockedUsersDB.contains(userID);
    }
}
