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

    public String storeItem(Item item, Properties serviceProperty) throws IOException, ClassNotFoundException {
        // updating DB for id -> item
        ItemDetailsDB itemDetailsDB = ItemDetailsDB.getInstance(serviceProperty);
        String itemID = itemDetailsDB.generateNewID();
        itemDetailsDB.addNewItem(item, itemID);

        // updating DB for auther -> {itemIDs}
        ProducerDetailsDB producerDetailsDB = ProducerDetailsDB.getInstance(serviceProperty);
        producerDetailsDB.addEntry(item.getProducers(), itemID);

        // updating DB for title -> {itemIDs}
        TitleDetailsDB titleDetailsDB = TitleDetailsDB.getInstance(serviceProperty);
        titleDetailsDB.addEntry(item.getTitle(), itemID);

        return itemID;
    }

    public void removeItem(String itemID, Properties serviceProperty) throws IOException, ClassNotFoundException {
        // updating DB for id -> item
        ItemDetailsDB itemDetailsDB = ItemDetailsDB.getInstance(serviceProperty);
        Item removedItem = itemDetailsDB.removeItem(itemID);

        // updating DB for auther -> {itemIDs}
        ProducerDetailsDB producerDetailsDB = ProducerDetailsDB.getInstance(serviceProperty);
        producerDetailsDB.removeEntry(removedItem.getProducers(), itemID);

        // updating DB for title -> {itemIDs}
        TitleDetailsDB titleDetailsDB = TitleDetailsDB.getInstance(serviceProperty);
        titleDetailsDB.removeEntry(removedItem.getTitle(), itemID);
    }
}
