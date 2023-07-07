package com.switchcase.renting.service.database;

import com.switchcase.renting.service.util.Item;

import java.io.IOException;
import java.util.*;

public class ItemDB {

    private static ItemDB obj;

    private ItemDB() {
    }

    public static ItemDB getInstance() {
        if (obj == null) {
            obj = new ItemDB();
        }
        return obj;
    }

    public void storeItem(Item item, Properties serviceProperty) throws IOException, ClassNotFoundException {
        ItemDetailsDB itemDetailsDB = ItemDetailsDB.getInstance(serviceProperty);
        String itemID = itemDetailsDB.generateNewID();
        itemDetailsDB.addNewItem(item, itemID);
    }
}
