package com.switchcase.renting.service.util;

public enum AccessType {
    LOG_IN,
    REGISTER;

    public static AccessType getAccessType(int value) {
        return values()[value];
    }
}
