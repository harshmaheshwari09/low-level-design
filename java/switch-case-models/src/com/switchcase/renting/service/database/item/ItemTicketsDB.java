package com.switchcase.renting.service.database.item;

import com.switchcase.database.model.Database;
import com.switchcase.renting.service.database.RentingServiceDB;
import com.switchcase.renting.service.model.user.Status;
import com.switchcase.renting.service.util.CustomRuntimeException;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ItemTicketsDB extends RentingServiceDB {

    private static ItemTicketsDB obj;
    private static Map<String, String> itemTicketDB;

    private ItemTicketsDB(Properties serviceProperties) throws IOException, ClassNotFoundException {
        super(serviceProperties);
    }

    public static ItemTicketsDB getInstance(Properties serviceProperty) throws IOException, ClassNotFoundException {
        if (obj == null) {
            obj = new ItemTicketsDB(serviceProperty);
        }
        return obj;
    }

    @Override
    public void loadDB(String filePath) throws IOException, ClassNotFoundException {
        if (itemTicketDB != null) {
            return;
        }
        if (!Files.isRegularFile(Paths.get(filePath))) {
            itemTicketDB = new HashMap<>();
        } else {
            itemTicketDB = (Map) Database.loadData(filePath);
        }
    }

    @Override
    public String getPath() {
        return ServiceProperty.ITEM_DB_PATH;
    }

    @Override
    public String getFileName() {
        return "itemTicketDB.ser";
    }

    public void addNewTicket(String itemID, String ticketID) throws IOException {
        itemTicketDB.put(itemID, ticketID);
        Database.storeData(itemTicketDB, getDbLocation());

    }

    public Status getItemStatus(String itemID) {
        return (itemTicketDB.containsKey(itemID) ? Status.NOT_AVAILABLE : Status.AVAILABLE);
    }

    public String removeEntry(String itemID) throws IOException {
        String ticketID = itemTicketDB.remove(itemID);
        Database.storeData(itemTicketDB, getDbLocation());
        return ticketID;
    }

    public String getTicketID(String itemID) {
        if (itemTicketDB.containsKey(itemID)) {
            return itemTicketDB.get(itemID);
        }
        throw CustomRuntimeException.invalidID();
    }
}
