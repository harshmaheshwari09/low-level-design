package com.switchcase.renting.service.model;

import java.io.Serializable;
import java.util.Date;

public class Ticket implements Serializable {
    String userID;
    String itemID;
    Date startDate;
    int numOfDays;
    private static final long serialVersionUID = 123456789L;

    public Ticket(String userID, String itemID, Date startDate, int numOfDays) {
        this.userID = userID;
        this.itemID = itemID;
        this.startDate = startDate;
        this.numOfDays = numOfDays;
    }
}
