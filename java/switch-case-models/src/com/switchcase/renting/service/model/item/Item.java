package com.switchcase.renting.service.model.item;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.model.user.Status;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Item implements Serializable {
    protected String title;
    protected Set<String> producers;
    protected Date dateOfProduction;
    protected Location location;
    protected Type type;
    private static final long serialVersionUID = 2L;

    public String getTitle() {
        return title;
    }

    public Set<String> getProducers() {
        return producers;
    }

    protected String getTitleFromUser(String message) {
        return ConsoleManager.getUserInput(message, input -> {
            if (input.length() > 0) {
                return input.trim();
            }
            throw new RuntimeException();
        });
    }

    protected Date getDateOfProductionFromUser(String message) {
        return ConsoleManager.getUserInput(message, input -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                return dateFormat.parse(input);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    protected Set<String> getProducersFromUser(String message) {
        return ConsoleManager.getUserInput(message, input -> {
            if (input.length() > 0) {
                return Arrays.stream(input.trim().split(",")).map(String::trim).collect(Collectors.toSet());
            }
            throw new RuntimeException();
        });
    }

    abstract public void print(String itemID, Status itemStatus);

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProducers(Set<String> producers) {
        this.producers = producers;
    }

    abstract public void showDetails();
}
