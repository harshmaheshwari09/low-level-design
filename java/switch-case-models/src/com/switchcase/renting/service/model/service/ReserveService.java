package com.switchcase.renting.service.model.service;

import com.switchcase.renting.service.database.item.ItemAvailabilityDB;
import com.switchcase.renting.service.database.item.ItemTicketsDB;
import com.switchcase.renting.service.database.ticket.TicketReferenceDB;
import com.switchcase.renting.service.database.user.UserTicketsDB;
import com.switchcase.renting.service.model.Ticket;
import com.switchcase.renting.service.util.CustomRuntimeException;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

// @Singleton
public class ReserveService {
    private static ReserveService obj;

    public static ReserveService getInstance() {
        if (obj == null) {
            obj = new ReserveService();
        }
        return obj;
    }

    public void bookItem(String userID, String itemID, Date bookingDate, int numOfDays, Properties serviceProperties)
        throws IOException, ClassNotFoundException {
        // check and update availability
        ItemAvailabilityDB itemAvailabilityDB = ItemAvailabilityDB.getInstance(serviceProperties);
        if (itemAvailabilityDB.isBooked(itemID, bookingDate, numOfDays)) {
            throw CustomRuntimeException.itemNotAvailable();
        }
        itemAvailabilityDB.updateAvailability(itemID, bookingDate, numOfDays, true);

        // create booking reference
        TicketReferenceDB ticketReferenceDB = TicketReferenceDB.getInstance(serviceProperties);
        String ticketID = ticketReferenceDB.addNewTicket(userID, itemID, bookingDate, numOfDays);

        UserTicketsDB userTicketsDB = UserTicketsDB.getInstance(serviceProperties);
        userTicketsDB.addNewTicket(userID, ticketID);

        ItemTicketsDB itemTicketsDB = ItemTicketsDB.getInstance(serviceProperties);
        itemTicketsDB.addNewTicket(itemID, ticketID);
    }

    public void returnItem(String userID, String itemID, Date returnDate, Properties serviceProperties)
        throws IOException, ClassNotFoundException {
        // start removing entries for corresponding ticket
        ItemTicketsDB itemTicketsDB = ItemTicketsDB.getInstance(serviceProperties);
        String ticketID = itemTicketsDB.removeEntry(itemID);

        TicketReferenceDB ticketReferenceDB = TicketReferenceDB.getInstance(serviceProperties);
        Ticket ticket = ticketReferenceDB.removeTicket(ticketID);

        UserTicketsDB userTicketsDB = UserTicketsDB.getInstance(serviceProperties);
        userTicketsDB.removeEntry(userID);

        ItemAvailabilityDB itemAvailabilityDB = ItemAvailabilityDB.getInstance(serviceProperties);
        itemAvailabilityDB.updateAvailability(itemID, ticket.getStartDate(), ticket.getNumOfDays(), false);
    }
}
