package com.switchcase.renting.service.database.item;

import com.switchcase.database.model.Database;
import com.switchcase.renting.service.database.RentingServiceDB;
import com.switchcase.renting.service.model.item.Item;
import com.switchcase.renting.service.util.CustomRuntimeException;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// @Singleton
public class ItemDetailsDB extends RentingServiceDB {

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

    public String addNewItem(Item item) throws IOException {
        String itemID = generateNewID(itemDetailsDB.keySet());
        itemDetailsDB.put(itemID, item);
        Database.storeData(itemDetailsDB, getDbLocation());
        return itemID;
    }

    public Item removeItem(String itemID) throws IOException {
        if (!itemDetailsDB.containsKey(itemID)) {
            throw CustomRuntimeException.invalidID();
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

    public Map<String, Item> getItems() {
        return itemDetailsDB;
    }

    public boolean isValidItemID(String itemID) {
        return itemDetailsDB.keySet().contains(itemID);
    }
}
