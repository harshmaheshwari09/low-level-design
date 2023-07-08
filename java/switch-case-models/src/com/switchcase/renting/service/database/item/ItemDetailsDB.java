package com.switchcase.renting.service.database.item;

import com.switchcase.database.model.Database;
import com.switchcase.renting.service.database.RentingServiceDB;
import com.switchcase.renting.service.util.CustomRuntimeException;
import com.switchcase.renting.service.util.Item;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

// @Singleton
public class ItemDetailsDB extends RentingServiceDB {

    private static final Random random = new Random();
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static ItemDetailsDB obj;
    private static Map<String, Item> itemDetailsDB;

    private ItemDetailsDB(Properties serviceProperty) throws IOException, ClassNotFoundException {
        super(serviceProperty);
    }

    public static ItemDetailsDB getInstance(Properties serviceProperty) throws IOException, ClassNotFoundException {
        if (obj == null) {
            obj = new ItemDetailsDB(serviceProperty);
        }
        return obj;
    }

    @Override
    public void loadDB(String filePath) throws IOException, ClassNotFoundException {
        if (itemDetailsDB != null) {
            return;
        }
        if (!Files.isRegularFile(Paths.get(filePath))) {
            itemDetailsDB = new HashMap<>();
        } else {
            itemDetailsDB = (Map) Database.loadData(filePath);
        }
    }

    // generate a valid 7 digit unique ID
    public String generateNewID() {
        String itemId;
        do {
            StringBuilder builder = new StringBuilder(7);
            for (int i = 0; i < 7; i++) {
                int randomIdx = random.nextInt(CHARACTERS.length());
                builder.append(CHARACTERS.charAt(randomIdx));
            }
            itemId = builder.toString();
        } while (itemDetailsDB.containsKey(itemId));
        return itemId;
    }

    public static void addNewItem(Item item, String itemID) throws IOException {
        itemDetailsDB.put(itemID, item);
        Database.storeData(itemDetailsDB, getDbLocation());
    }

    public Item removeItem(String itemID) throws IOException {
        if (!itemDetailsDB.containsKey(itemID)) {
            throw CustomRuntimeException.invalidBookID();
        }
        Item removedItem = itemDetailsDB.remove(itemID);
        Database.storeData(itemDetailsDB, getDbLocation());
        return removedItem;
    }

    @Override
    public String getPath() {
        return ServiceProperty.ITEM_DB_PATH;
    }

    @Override
    public String getFileName() {
        return "itemInfo.ser";
    }
}
