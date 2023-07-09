package com.switchcase.renting.service.model.service;

import java.util.Date;

// @Singleton
public class ReserveService {
    private static ReserveService obj;

    public static ReserveService getInstance() {
        if (obj == null) {
            obj = new ReserveService();
        }
        return obj;
    }

    public void bookItem(String userID, String itemID, Date bookingDate, int numOfDays) {

    }

    public void returnItem(String userID, String itemID, Date returnDate) {

    }
}
