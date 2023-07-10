package com.switchcase.renting.service.util;

import com.switchcase.renting.service.model.Operation;

public enum LibraryOperations implements Operation {
    DISPLAY_USER_PROFILE,
    DISPLAY_TICKET_DETAILS,
    DISPLAY_BOOK_DETAILS,
    SEARCH_BOOK,
    ISSUE_BOOK,
    RETURN_BOOK,
    RE_ISSUE_BOOK,
    ADD_BOOK,
    REMOVE_BOOK,
    BLOCK_USER,
    UNBLOCK_USER;

    public static Operation[] getManagerOperations() {
        return LibraryOperations.values();
    }

    public static Operation[] getUserOperations() {
        return new Operation[]{
            DISPLAY_USER_PROFILE,
            DISPLAY_TICKET_DETAILS,
            DISPLAY_BOOK_DETAILS,
            SEARCH_BOOK,
            ISSUE_BOOK,
            RETURN_BOOK,
            RE_ISSUE_BOOK,
        };
    }
}
