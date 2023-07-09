package com.switchcase.renting.service.database.user;

import com.switchcase.database.model.Database;
import com.switchcase.renting.service.database.RentingServiceDB;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class UserTicketsDB extends RentingServiceDB {

    private static UserTicketsDB obj;
    private static Map<String, Set<String>> userTicketDB;

    private UserTicketsDB(Properties serviceProperties) throws IOException, ClassNotFoundException {
        super(serviceProperties);
    }

    public static UserTicketsDB getInstance(Properties serviceProperty) throws IOException, ClassNotFoundException {
        if (obj == null) {
            obj = new UserTicketsDB(serviceProperty);
        }
        return obj;
    }

    @Override
    public void loadDB(String filePath) throws IOException, ClassNotFoundException {
        if (userTicketDB != null) {
            return;
        }
        if (!Files.isRegularFile(Paths.get(filePath))) {
            userTicketDB = new HashMap<>();
        } else {
            userTicketDB = (Map) Database.loadData(filePath);
        }
    }

    @Override
    public String getPath() {
        return ServiceProperty.USER_DB_PATH;
    }

    @Override
    public String getFileName() {
        return "userTicketDB.ser";
    }

    public void addNewTicket(String userID, String ticketID) throws IOException {
        if (!userTicketDB.containsKey(userID)) {
            userTicketDB.put(userID, new HashSet<>());
        }
        userTicketDB.get(userID).add(ticketID);
        Database.storeData(userTicketDB, getDbLocation());

    }
}
