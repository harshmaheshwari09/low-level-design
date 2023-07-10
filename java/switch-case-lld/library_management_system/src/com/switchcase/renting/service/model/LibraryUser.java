package com.switchcase.renting.service.model;

import com.switchcase.games.util.ConsoleManager;
import com.switchcase.renting.service.database.item.ItemDetailsDB;
import com.switchcase.renting.service.database.item.ItemTicketsDB;
import com.switchcase.renting.service.database.ticket.TicketReferenceDB;
import com.switchcase.renting.service.database.user.BlockedUserDB;
import com.switchcase.renting.service.database.user.CustomerDetailsDB;
import com.switchcase.renting.service.database.user.UserDetailsDB;
import com.switchcase.renting.service.database.user.UserTicketsDB;
import com.switchcase.renting.service.model.item.Item;
import com.switchcase.renting.service.model.service.DisplayService;
import com.switchcase.renting.service.model.service.ReserveService;
import com.switchcase.renting.service.model.user.Status;
import com.switchcase.renting.service.model.user.User;
import com.switchcase.renting.service.util.CustomRuntimeException;
import com.switchcase.renting.service.util.LibraryOperations;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class LibraryUser extends User {
    private static final long serialVersionUID = 12L;

    @Override
    public void performOperation(Operation operation, Properties serviceProperty)
        throws IOException, ClassNotFoundException {
        switch ((LibraryOperations) operation) {
            // display service
            case SEARCH_BOOK -> searchBook(serviceProperty);
            case DISPLAY_TICKET_DETAILS -> displayTicketDetails(serviceProperty);
            case DISPLAY_USER_PROFILE -> displayUserProfile(serviceProperty);
            case DISPLAY_BOOK_DETAILS -> displayBookDetails(serviceProperty);

            // reserve service
            case ISSUE_BOOK -> issueBook(serviceProperty);
            case RETURN_BOOK -> returnBook(serviceProperty);
            case RE_ISSUE_BOOK -> reissueBook(serviceProperty);

            // admin service
            case ADD_BOOK -> addBook(serviceProperty);
            case REMOVE_BOOK -> removeBook(serviceProperty);
            case BLOCK_USER -> blockUser(serviceProperty, operation, true);
            case UNBLOCK_USER -> blockUser(serviceProperty, operation, false);
        }
    }

    private void reissueBook(Properties serviceProperty) throws IOException, ClassNotFoundException {
        returnBook(serviceProperty);
        issueBook(serviceProperty);
    }

    private void displayBookDetails(Properties serviceProperty) throws IOException, ClassNotFoundException {
        DisplayService displayService = DisplayService.getInstance();
        displayService.displayItemDetails(serviceProperty);
    }

    private void displayTicketDetails(Properties serviceProperty) throws IOException, ClassNotFoundException {
        DisplayService displayService = DisplayService.getInstance();
        displayService.displayTicketDetails(serviceProperty);
    }

    abstract void displayUserProfile(Properties serviceProperty) throws IOException, ClassNotFoundException;

    protected void returnBook(Properties serviceProperties, String userID) throws IOException, ClassNotFoundException {
        String bookID = ConsoleManager.readLine("Enter the bookID: ");
        Date returnDate = ConsoleManager.getUserInput("Enter the return date(dd/mm/yyyy): ", input -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                return dateFormat.parse(input);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        // validate entry
        ItemTicketsDB itemTicketsDB = ItemTicketsDB.getInstance(serviceProperties);
        String ticketID = itemTicketsDB.getTicketID(bookID);
        TicketReferenceDB ticketReferenceDB = TicketReferenceDB.getInstance(serviceProperties);
        try {
            if (!ticketReferenceDB.isValidRequest(ticketID, userID, bookID)) {
                throw CustomRuntimeException.invalidID();
            }
        } catch (Exception exception) {
            ConsoleManager.print(exception.getMessage());
            return;
        }
        Ticket ticket = ticketReferenceDB.getTicket(ticketID);
        int fine = calculateFine(ticket, returnDate);
        if (fine > 0) {
            ConsoleManager.print(String.format("======== PLEASE COLLECT FINE : %d ======\n", fine));
            ConsoleManager.getUserInput("Press 'y' to continue: ", input -> {
                if ("y".equals(input.trim().toLowerCase())) {
                    return "";
                }
                throw new RuntimeException();
            });
        }
        try {
            ReserveService reserveService = ReserveService.getInstance();
            reserveService.returnItem(userID, bookID, returnDate, serviceProperties);
        } catch (Exception exception) {
            ConsoleManager.print(exception.getMessage());
            return;
        }
        ConsoleManager.print(String.format("======== BOOK(id# %s) RETURNED SUCCESSFULLY by %s ======", bookID, userID));

    }

    private int calculateFine(Ticket ticket, Date returnDate) {
        int extraDays = (int) Math.max(
            ((returnDate.getTime() - ticket.getStartDate().getTime()) / (24 * 60 * 60 * 1000)) - ticket.getNumOfDays(),
            0);

        // INR 10/- fine per day
        return extraDays * 10;
    }

    abstract void returnBook(Properties serviceProperty) throws IOException, ClassNotFoundException;

    protected void issueBook(Properties serviceProperties, String userID) throws IOException, ClassNotFoundException {
        ItemDetailsDB itemDetailsDB = ItemDetailsDB.getInstance(serviceProperties);
        String bookID = ConsoleManager.getUserInput("Enter the bookID: ", input -> {
            if (itemDetailsDB.isValidItemID(input.trim())) {
                return input.trim();
            }
            throw CustomRuntimeException.invalidID();
        });
        Date issueDate = ConsoleManager.getUserInput("Enter the issue date(dd/mm/yyyy): ", input -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                return dateFormat.parse(input);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            ItemTicketsDB itemTicketsDB = ItemTicketsDB.getInstance(serviceProperties);
            if (itemTicketsDB.getItemStatus(bookID) == Status.NOT_AVAILABLE) {
                throw CustomRuntimeException.itemNotAvailable();
            }
            ReserveService reserveService = ReserveService.getInstance();
            reserveService.bookItem(userID, bookID, issueDate, 10, serviceProperties);
        } catch (CustomRuntimeException exception) {
            ConsoleManager.print(exception.getMessage());
            return;
        }
        ConsoleManager.print(String.format("======== BOOK(id# %s) ISSUED SUCCESSFULLY to %s ======", bookID, userID));
    }

    abstract void issueBook(Properties serviceProperty) throws IOException, ClassNotFoundException;

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
                "\n| %-7s | %-20s | %-20s | %-18s | %-8s | %-8s | %-12s |\n",
                "BookID", "Title", "Authers", "Publication Date", "Genre", "Shelf #", "Status")
        );
        ConsoleManager.print("-".repeat(112));
        displayService.searchItem(book, serviceProperty);
    }
}
