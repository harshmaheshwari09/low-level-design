package com.switchcase.renting.service.model;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.util.Genre;
import com.switchcase.renting.service.util.Item;
import com.switchcase.renting.service.util.Location;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Book extends Item {

    Set<String> authers;
    Genre genre;

    public static Book createNewBook() {
        return
            new Book()
                .buildTitle()
                .buildAuthers()
                .buildDateOfPublication()
                .buildGenreAndLocation();
    }

    private Book buildGenreAndLocation() {
        Genre[] genres = Genre.values();
        int id = Genre.selectGenre(genres);
        this.genre = genres[id];
        this.location = new Location(id);
        return this;
    }

    private Book buildDateOfPublication() {
        this.dateOfProduction = getDateOfProduction();
        return this;
    }

    private Book buildAuthers() {
        this.authers = ConsoleManager.getUserInput("Enter comma(\",\") separated names of Authers: ", input -> {
            if (input.length() > 0) {
                return Arrays.stream(input.trim().split(",")).map(String::trim).collect(Collectors.toSet());
            }
            throw new RuntimeException();
        });
        return this;
    }

    private Book buildTitle() {
        this.title = getTitle("\nEnter the book title: ");
        return this;
    }


}
