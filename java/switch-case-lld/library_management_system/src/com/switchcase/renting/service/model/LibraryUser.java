package com.switchcase.renting.service.model;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.model.service.DisplayService;
import com.switchcase.renting.service.model.user.User;
import com.switchcase.renting.service.util.LibraryOperations;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class LibraryUser extends User {

    @Override
    public void performOperation(Operation operation, Properties serviceProperty)
        throws IOException, ClassNotFoundException {
        switch ((LibraryOperations) operation) {
            // display service
            case SEARCH_BOOK -> searchBook(serviceProperty);
            case SHOW_PROFILE -> System.out.println("display service: " + operation);

            // reserve service
            case ISSUE_BOOK,
                RETURN_BOOK,
                RE_ISSUE_BOOK-> System.out.println("reserve service: " + operation);

            // admin service
            case ADD_BOOK -> addBook(serviceProperty);
            case REMOVE_BOOK -> removeBook(serviceProperty);
            case BLOCK_USER -> blockUser(serviceProperty, operation, true);
            case UNBLOCK_USER -> blockUser(serviceProperty, operation, false);
        }
    }

    abstract void addBook(Properties serviceProperty) throws IOException, ClassNotFoundException;

    abstract void removeBook(Properties serviceProperty);

    abstract void blockUser(Properties serviceProperty, Operation operation, boolean shouldBlock) throws IOException, ClassNotFoundException;


    protected void searchBook(Properties serviceProperty) throws IOException, ClassNotFoundException {
        Book book = new Book();
        String title = ConsoleManager.readLine("Enter Title(optional): ");
        if (title.trim().length() > 0) {
            book.setTitle(title.trim());
        }
        Set<String> authers =
            Arrays.stream(ConsoleManager.readLine("Enter comma(',') separated Authers(optional): ")
                    .trim()
                    .split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
        authers.remove("");
        if (authers.size() > 0) {
            book.setProducers(authers);
        }

        DisplayService displayService = DisplayService.getInstance();
        ConsoleManager.print(
            String.format(
                "\n| %-7s | %-20s | %-20s | %-18s | %-8s | %-8s |\n",
                "BookID", "Title", "Authers", "Publication Date", "Genre", "Shelf #")
        );
        ConsoleManager.print("-".repeat(100));
        displayService.searchItem(book, serviceProperty);
    }
}
