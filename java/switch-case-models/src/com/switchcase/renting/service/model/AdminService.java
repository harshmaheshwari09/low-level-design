package com.switchcase.renting.service.model;

import com.switchcase.renting.service.database.ItemDB;
import com.switchcase.renting.service.util.Item;

import java.io.IOException;
import java.util.Properties;

// @Singleton
public class AdminService {

    private static AdminService obj;

    public static AdminService getInstance() {
        if (obj == null) {
            obj = new AdminService();
        }
        return obj;
    }

    public String addItem(Item item, Properties serviceProperty) throws IOException, ClassNotFoundException {
        ItemDB itemDB = ItemDB.getInstance();
        return itemDB.storeItem(item, serviceProperty);
    }

    public boolean removeItem(int itemID) {
        return false;
    }

    public boolean blockUser(int userID) {
        return false;
    }

    public boolean unBlockUser(int userID) {
        return false;
    }
}
