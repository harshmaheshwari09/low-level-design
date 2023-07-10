package com.switchcase.renting.service.database.item;

import com.switchcase.database.model.Database;
import com.switchcase.renting.service.database.RentingServiceDB;
import com.switchcase.renting.service.util.ServiceProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ItemAvailabilityDB extends RentingServiceDB {

    private static ItemAvailabilityDB obj;
    private static Map<String, boolean[]> itemAvailabilityDB;

    private ItemAvailabilityDB(Properties serviceProperty) throws IOException, ClassNotFoundException {
        super(serviceProperty);
    }

    public static ItemAvailabilityDB getInstance(Properties serviceProperty) throws IOException, ClassNotFoundException {
        if (obj == null) {
            obj = new ItemAvailabilityDB(serviceProperty);
        }
        return obj;
    }

    @Override
    public String getPath() {
        return ServiceProperty.ITEM_DB_PATH;
    }

    @Override
    public String getFileName() {
        return "itemAvailabilityDB.ser";
    }

    @Override
    public void loadDB(String filePath) throws IOException, ClassNotFoundException {
        if (itemAvailabilityDB != null) {
            return;
        }
        if (!Files.isRegularFile(Paths.get(filePath))) {
            itemAvailabilityDB = new HashMap<>();
        } else {
            itemAvailabilityDB = (Map) Database.loadData(filePath);
        }
    }

    public void updateAvailability(String itemID, Date bookingDate, int numOfDays, boolean status) throws IOException {
        int startDay = getStartDay(bookingDate);
        boolean[] calendar = itemAvailabilityDB.get(itemID);
        for (int idx = startDay; idx < startDay + numOfDays; idx++) {
            calendar[idx] = status;
        }
        itemAvailabilityDB.put(itemID, calendar);
        Database.storeData(itemAvailabilityDB, getDbLocation());
    }

    private int getStartDay(Date bookingDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bookingDate);

        int year = calendar.get(Calendar.YEAR);
        while (!isLeapYear(year)) {
            year--;
        }

        calendar.set(year, Calendar.JANUARY, 1);
        return daysBetween(calendar.getTime(), bookingDate);
    }

    private int daysBetween(Date leapYearStartDate, Date currentDate) {
        long startTime = leapYearStartDate.getTime();
        long currentTime = currentDate.getTime();
        long millisecondsPerDay = 24 * 60 * 60 * 1000;
        return (int) ((currentTime - startTime) / millisecondsPerDay);
    }

    private boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    public boolean isBooked(String itemID, Date bookingDate, int numOfDays) {
        int startDay = getStartDay(bookingDate);
        if (!itemAvailabilityDB.containsKey(itemID)) {
            itemAvailabilityDB.put(itemID, new boolean[1461]);
        }
        boolean[] calendar = itemAvailabilityDB.get(itemID);
        for (int idx = startDay; idx < startDay + numOfDays; idx++) {
            if (calendar[idx]) {
                return true;
            }
        }
        return false;
    }
}
