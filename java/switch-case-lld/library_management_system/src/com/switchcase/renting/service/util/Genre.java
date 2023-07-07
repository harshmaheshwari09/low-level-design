package com.switchcase.renting.service.util;

import com.switchcase.games.util.ConsoleManager;

public enum Genre {
    FICTION,
    NON_FICTION,
    MYSTERY,
    THRILLER,
    FANTASY,
    ROMANCE,
    AUTOBIOGRAPHY,
    SCIENCE,
    SPIRITUALITY,
    HISTORY,
    MANGA;

    public static int selectGenre(Genre[] genres) {
        ConsoleManager.print("Please select a genre for the book: ");
        for (int idx = 0; idx < genres.length; idx++) {
            ConsoleManager.print(String.format("\n Press %d for %s", idx, genres[idx]));
        }
        return ConsoleManager.getUserInput("\n", input -> {
            int value = Integer.parseInt(input);
            if (0 <= value && value < genres.length) {
                return value;
            }
            throw new RuntimeException();
        });
    }
}
