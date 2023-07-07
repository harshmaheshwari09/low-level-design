package com.switchcase.renting.service.util;

import com.switchcase.games.util.ConsoleManager;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Item implements Serializable {
    protected String title;
    protected Date dateOfProduction;
    protected Location location;

    protected String getTitle(String message) {
        return ConsoleManager.getUserInput(message, input -> {
            if (input.length() > 0) {
                return input.trim();
            }
            throw new RuntimeException();
        });
    }

    protected Date getDateOfProduction() {
        return ConsoleManager.getUserInput("Enter date of publication (dd/mm/yyyy): ", input -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                return dateFormat.parse(input);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
