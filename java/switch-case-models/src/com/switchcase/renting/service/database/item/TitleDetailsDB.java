package com.switchcase.renting.service.database.item;

import com.switchcase.database.model.Database;
import com.switchcase.renting.service.database.RentingServiceDB;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// @Singleton
public class TitleDetailsDB extends RentingServiceDB {

    private static TitleDetailsDB obj;
    private static Map<String, Set<String>> titleDetailsDB;

    private TitleDetailsDB(Properties serviceProperty) throws IOException, ClassNotFoundException {
        super(serviceProperty);
    }

    public static TitleDetailsDB getInstance(Properties serviceProperty) throws IOException, ClassNotFoundException {
        if (obj == null) {
            obj = new TitleDetailsDB(serviceProperty);
        }
        return obj;
    }

    @Override
    public String getPath() {
        return ServiceProperty.ITEM_DB_PATH;
    }

    @Override
    public String getFileName() {
        return "titleDB.ser";
    }

    @Override
    public void loadDB(String filePath) throws IOException, ClassNotFoundException {
        if (titleDetailsDB != null) {
            return;
        }
        if (!Files.isRegularFile(Paths.get(filePath))) {
            titleDetailsDB = new HashMap<>();
        } else {
            titleDetailsDB = (Map) Database.loadData(filePath);
        }
    }

    public void addEntry(String title, String itemID) throws IOException {
        if (!titleDetailsDB.containsKey(title)) {
            titleDetailsDB.put(title, new HashSet<>());
        }
        titleDetailsDB.get(title).add(itemID);
        Database.storeData(titleDetailsDB, getDbLocation());
    }

    public void removeEntry(String title, String itemID) throws IOException {
        titleDetailsDB.get(title).remove(itemID);
        if (titleDetailsDB.get(title).size() == 0) {
            titleDetailsDB.remove(title);
        }
        Database.storeData(titleDetailsDB, getDbLocation());
    }
}
