package com.switchcase.renting.service.model.service;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.database.item.ItemDetailsDB;
import com.switchcase.renting.service.database.item.ItemTicketsDB;
import com.switchcase.renting.service.database.ticket.TicketReferenceDB;
import com.switchcase.renting.service.database.user.BlockedUserDB;
import com.switchcase.renting.service.database.user.CustomerDetailsDB;
import com.switchcase.renting.service.database.user.UserDetailsDB;
import com.switchcase.renting.service.database.user.UserTicketsDB;
import com.switchcase.renting.service.model.Ticket;
import com.switchcase.renting.service.model.item.Item;
import com.switchcase.renting.service.model.user.Status;
import com.switchcase.renting.service.model.user.User;
import com.switchcase.renting.service.util.CustomRuntimeException;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

// @Singleton
public class DisplayService {
    private static DisplayService obj;

    public static DisplayService getInstance() {
        if (obj == null) {
            obj = new DisplayService();
        }
        return obj;
    }

    public void displayTicketDetails(Properties serviceProperty) throws IOException, ClassNotFoundException {
        TicketReferenceDB ticketReferenceDB = TicketReferenceDB.getInstance(serviceProperty);
        String ticketID = ConsoleManager.getUserInput("Enter the ticket ref #: ", input -> {
            if (!ticketReferenceDB.isValidTicket(input.trim())) {
                throw CustomRuntimeException.invalidID();
            }
            return input.trim();
        });
        Ticket ticket = ticketReferenceDB.getTicket(ticketID);
        ticket.showDetails();
    }

    public void displayItemDetails(Properties serviceProperty) throws IOException, ClassNotFoundException {
        ItemDetailsDB itemDetailsDB = ItemDetailsDB.getInstance(serviceProperty);
        String itemID = ConsoleManager.getUserInput("Enter the itemID : ", input -> {
            if (!itemDetailsDB.isValidItemID(input.trim())) {
                throw CustomRuntimeException.invalidID();
            }
            return input.trim();
        });
        Item item = itemDetailsDB.getItem(itemID);
        item.showDetails();
    }

    public void displayUserProfile(Properties serviceProperties, String userID) throws IOException, ClassNotFoundException {
        UserDetailsDB userDetailsDB = CustomerDetailsDB.getInstance(serviceProperties);
        try {
            if (!userDetailsDB.isValidID(userID)) {
                throw CustomRuntimeException.invalidID();
            }
        } catch (Exception exception) {
            ConsoleManager.print(exception.getMessage());
            return;
        }
        ConsoleManager.print("======== USER DETAILS : ======\n");
        User user = userDetailsDB.getUser(userID);
        user.printDetails();

        BlockedUserDB blockedUserDB = BlockedUserDB.getInstance(serviceProperties);
        Status status = (blockedUserDB.isBlocked(userID) ? Status.BLOCKED : Status.ACTIVE);
        ConsoleManager.print(String.format("Status : %s\n", status));

        UserTicketsDB userTicketsDB = UserTicketsDB.getInstance(serviceProperties);
        Set<String> tickets = userTicketsDB.getTicketIDs(userID);
        ConsoleManager.print(String.format("Issued item tickets : %s\n", tickets));
    }

    public void searchItem(Item requiredItem, Properties serviceProperty) throws IOException, ClassNotFoundException {
        ItemTicketsDB itemTicketsDB = ItemTicketsDB.getInstance(serviceProperty);
        ItemDetailsDB itemDetailsDB = ItemDetailsDB.getInstance(serviceProperty);
        for (var entry : itemDetailsDB.getItems().entrySet()) {
            if (isRequired(entry, requiredItem)) {
                print(entry, itemTicketsDB);
            }
        }
    }

    private void print(Map.Entry<String, Item> entry, ItemTicketsDB itemTicketsDB) {
        String itemID = entry.getKey();
        Status itemStatus = itemTicketsDB.getItemStatus(itemID);
        Item item = entry.getValue();
        item.print(itemID, itemStatus);
    }

    private boolean isRequired(Map.Entry<String, Item> entry, Item requiredItem) {
        if (requiredItem.getTitle() != null && !requiredItem.getTitle().equals(entry.getValue().getTitle())) {
            return false;
        }

        if (requiredItem.getProducers() != null
            && !entry.getValue().getProducers().containsAll(requiredItem.getProducers())) {
            return false;
        }
        return true;
    }
}
