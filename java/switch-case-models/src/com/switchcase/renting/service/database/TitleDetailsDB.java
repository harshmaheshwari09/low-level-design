package com.switchcase.renting.service.database;

import com.switchcase.database.model.Database;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// @Singleton
public class TitleDetailsDB {

    private static TitleDetailsDB obj;
    private static Map<String, Set<String>> titleDetailsDB;
    private static String dbLocation;
    private final static String TITLE_DB_FILENAME = "titleDB.ser";

    private TitleDetailsDB(Properties serviceProperty) throws IOException, ClassNotFoundException {
        dbLocation = getDatabaseLocation(serviceProperty);
        loadDB(dbLocation);
    }

    public static TitleDetailsDB getInstance(Properties serviceProperty) throws IOException, ClassNotFoundException {
        if (obj == null) {
            obj = new TitleDetailsDB(serviceProperty);
        }
        return obj;
    }

    private static String getDatabaseLocation(Properties serviceProperty) {
        return ServiceProperty.SRC_DIRECTORY
            + serviceProperty.getProperty(ServiceProperty.USER_DATABASE_LOCATION)
            + "/item/"
            + TITLE_DB_FILENAME;
    }

    private static void loadDB(String filePath) throws IOException, ClassNotFoundException {
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
        Database.storeData(titleDetailsDB, dbLocation);
    }

    public void removeEntry(String title, String itemID) throws IOException {
        titleDetailsDB.get(title).remove(itemID);
        if (titleDetailsDB.get(title).size() == 0) {
            titleDetailsDB.remove(title);
        }
        Database.storeData(titleDetailsDB, dbLocation);
    }
}
