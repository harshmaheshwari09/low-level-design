package com.switchcase.renting.service.database.ticket;

import com.switchcase.database.model.Database;
import com.switchcase.renting.service.database.RentingServiceDB;
import com.switchcase.renting.service.model.Ticket;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TicketReferenceDB extends RentingServiceDB {

    private static TicketReferenceDB obj;
    private static Map<String, Ticket> ticketDetailsDB;

    private TicketReferenceDB(Properties serviceProperty) throws IOException, ClassNotFoundException {
        super(serviceProperty);
    }

    public static TicketReferenceDB getInstance(Properties serviceProperty) throws IOException, ClassNotFoundException {
        if (obj == null) {
            obj = new TicketReferenceDB(serviceProperty);
        }
        return obj;
    }

    @Override
    protected void loadDB(String filePath) throws IOException, ClassNotFoundException {
        if (ticketDetailsDB != null) {
            return;
        }
        if (!Files.isRegularFile(Paths.get(filePath))) {
            ticketDetailsDB = new HashMap<>();
        } else {
            ticketDetailsDB = (Map) Database.loadData(filePath);
        }
    }

    @Override
    protected String getPath() {
        return ServiceProperty.TICKET_DB_PATH;
    }

    @Override
    protected String getFileName() {
        return "ticketDetails.ser";
    }

    public String addNewTicket(String userID, String itemID, Date startDate, int numOfDays) throws IOException {
        String ticketID = generateNewID(ticketDetailsDB.keySet());
        Ticket ticket = new Ticket(userID, itemID, startDate, numOfDays);
        ticketDetailsDB.put(ticketID, ticket);
        Database.storeData(ticketDetailsDB, getDbLocation());
        return ticketID;
    }
}
