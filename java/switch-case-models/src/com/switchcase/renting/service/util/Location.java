package com.switchcase.renting.service.util;

import java.io.Serializable;

public class Location implements Serializable {
    Integer x;
    Integer y;
    Integer z;

    public Location(int x) {
        this.x = x;
    }
}
