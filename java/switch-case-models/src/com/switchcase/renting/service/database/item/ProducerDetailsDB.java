package com.switchcase.renting.service.database.item;

import com.switchcase.database.model.Database;
import com.switchcase.renting.service.database.RentingServiceDB;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// @Singleton
public class ProducerDetailsDB extends RentingServiceDB {

    private static ProducerDetailsDB obj;
    private static Map<String, Set<String>> producerDetailsDB;

    private ProducerDetailsDB(Properties serviceProperty) throws IOException, ClassNotFoundException {
        super(serviceProperty);
    }

    public static ProducerDetailsDB getInstance(Properties serviceProperty) throws IOException, ClassNotFoundException {
        if (obj == null) {
            obj = new ProducerDetailsDB(serviceProperty);
        }
        return obj;
    }

    @Override
    public String getPath() {
        return ServiceProperty.ITEM_DB_PATH;
    }

    @Override
    public String getFileName() {
        return "autherDB.ser";
    }

    @Override
    public void loadDB(String filePath) throws IOException, ClassNotFoundException {
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
        Database.storeData(producerDetailsDB, getDbLocation());
    }

    public void removeEntry(Set<String> producers, String itemID) throws IOException {
        for (var producer : producers) {
            producerDetailsDB.get(producer).remove(itemID);
            if (producerDetailsDB.get(producer).size() == 0) {
                producerDetailsDB.remove(producer);
            }
        }
        Database.storeData(producerDetailsDB, getDbLocation());
    }
}
