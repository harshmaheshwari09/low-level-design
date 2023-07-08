package com.switchcase.renting.service.database.user;

import com.switchcase.database.model.Database;
import com.switchcase.renting.service.database.RentingServiceDB;
import com.switchcase.renting.service.database.item.ItemDetailsDB;
import com.switchcase.renting.service.user.User;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BlockedUserDB extends RentingServiceDB {

    private static BlockedUserDB obj;
    private Set<String> blockedUsers;

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
        if (blockedUsers != null) {
            return;
        }
        if (!Files.isRegularFile(Paths.get(filePath))) {
            blockedUsers = new HashSet<>();
        } else {
            blockedUsers = (Set) Database.loadData(filePath);
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
}
