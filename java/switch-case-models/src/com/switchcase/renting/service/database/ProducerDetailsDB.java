package com.switchcase.renting.service.database;

import com.switchcase.database.model.Database;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// @Singleton
public class ProducerDetailsDB {

    private static ProducerDetailsDB obj;
    private static Map<String, Set<String>> producerDetailsDB;
    private static String dbLocation;
    private final static String PRODUCER_DB_FILENAME = "autherDB.ser";

    private ProducerDetailsDB(Properties serviceProperty) throws IOException, ClassNotFoundException {
        dbLocation = getDatabaseLocation(serviceProperty);
        loadDB(dbLocation);
    }

    public static ProducerDetailsDB getInstance(Properties serviceProperty) throws IOException, ClassNotFoundException {
        if (obj == null) {
            obj = new ProducerDetailsDB(serviceProperty);
        }
        return obj;
    }

    private static String getDatabaseLocation(Properties serviceProperty) {
        return ServiceProperty.SRC_DIRECTORY
            + serviceProperty.getProperty(ServiceProperty.USER_DATABASE_LOCATION)
            + "/item/"
            + PRODUCER_DB_FILENAME;
    }

    private static void loadDB(String filePath) throws IOException, ClassNotFoundException {
        if (producerDetailsDB != null) {
            return;
        }
        if (!Files.isRegularFile(Paths.get(filePath))) {
            producerDetailsDB = new HashMap<>();
        } else {
            producerDetailsDB = (Map) Database.loadData(filePath);
        }
    }

    public void addEntry(Set<String> producers, String itemID) throws IOException {
        for (var producer : producers) {
            if (!producerDetailsDB.containsKey(producer)) {
                producerDetailsDB.put(producer, new HashSet<>());
            }
            producerDetailsDB.get(producer).add(itemID);
        }
        Database.storeData(producerDetailsDB, dbLocation);
    }

    public void removeEntry(Set<String> producers, String itemID) throws IOException {
        for (var producer : producers) {
            producerDetailsDB.get(producer).remove(itemID);
            if (producerDetailsDB.get(producer).size() == 0) {
                producerDetailsDB.remove(producer);
            }
        }
        Database.storeData(producerDetailsDB, dbLocation);
    }
}
