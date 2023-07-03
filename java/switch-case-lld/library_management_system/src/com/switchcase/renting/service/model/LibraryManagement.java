package com.switchcase.renting.service.model;

import com.switchcase.renting.service.user.User;

import java.io.IOException;

public class LibraryManagement extends RentingService {
    private static final String RELATIVE_PROPERTY_FILE_PATH =
        "/java/switch-case-lld/library_management_system/src/com/switchcase/renting/service/generic/library_management_system.properties";

    public LibraryManagement(User user) throws IOException, ClassNotFoundException {
        super(user);
    }

    @Override
    String getAbsolutePropertyFilePath() {
        return System.getProperty("user.dir") + RELATIVE_PROPERTY_FILE_PATH;
    }
}
