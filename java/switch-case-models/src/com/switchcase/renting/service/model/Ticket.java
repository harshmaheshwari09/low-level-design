package com.switchcase.renting.service.model;

import com.switchcase.games.util.ConsoleManager;

import java.io.Serializable;
import java.util.Date;

public class Ticket implements Serializable {
    String userID;
    String itemID;
    Date startDate;
    int numOfDays;
    private static final long serialVersionUID = 3L;

    public String getUserID() {
        return userID;
    }

    public String getItemID() {
        return itemID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public Ticket(String userID, String itemID, Date startDate, int numOfDays) {
        this.userID = userID;
        this.itemID = itemID;
        this.startDate = startDate;
        this.numOfDays = numOfDays;
    }

    public void showDetails() {
        ConsoleManager.print("======== TICKET DETAILS : ======\n");
        ConsoleManager.print(String.format("Rented by: %s\n", userID));
        ConsoleManager.print(String.format("ItemID : %s\n", itemID));
        ConsoleManager.print(String.format("Rented on: %tF\n", startDate));
        ConsoleManager.print(String.format("Rented for %d days\n", numOfDays));
    }
}
