package com.switchcase.renting.service.model.service;

import com.switchcase.renting.service.database.item.ItemDetailsDB;
import com.switchcase.renting.service.database.item.ProducerDetailsDB;
import com.switchcase.renting.service.database.item.TitleDetailsDB;
import com.switchcase.renting.service.database.user.BlockedUserDB;
import com.switchcase.renting.service.model.item.Item;

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

    public void blockUser(String userID, Properties serviceProperty, boolean shouldBlock) throws IOException, ClassNotFoundException {
        BlockedUserDB blockedUserDB = BlockedUserDB.getInstance(serviceProperty);
        blockedUserDB.blockUser(userID, shouldBlock);
    }
}
