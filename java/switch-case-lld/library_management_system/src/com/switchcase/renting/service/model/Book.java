package com.switchcase.renting.service.model;

import com.switchcase.renting.service.util.Genre;
import com.switchcase.renting.service.util.Item;
import com.switchcase.renting.service.util.Location;

public class Book extends Item {

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
        this.dateOfProduction = getDateOfProductionFromUser("Enter date of publication (dd/mm/yyyy): ");
        return this;
    }

    private Book buildAuthers() {
        this.producers = getProducersFromUser("Enter comma(\",\") separated names of Authers: ");
        return this;
    }

    private Book buildTitle() {
        this.title = getTitleFromUser("\nEnter the book title: ");
        return this;
    }


}
