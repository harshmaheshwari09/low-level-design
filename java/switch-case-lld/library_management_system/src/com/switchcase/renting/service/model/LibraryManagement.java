package com.switchcase.renting.service.model;

import com.switchcase.renting.service.model.service.RentingService;

public class LibraryManagement extends RentingService {
    private static final String RELATIVE_PROPERTY_FILE_PATH =
        "/java/switch-case-lld/library_management_system/src/com/switchcase/renting/service/generic/library_management_system.properties";

    @Override
    public String getAbsolutePropertyFilePath() {
        return System.getProperty("user.dir") + RELATIVE_PROPERTY_FILE_PATH;
    }
}
