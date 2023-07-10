package com.switchcase.renting.service.model.item;

import java.io.Serializable;

public class Location implements Serializable {
    Integer x;
    Integer y;
    Integer z;
    private static final long serialVersionUID = 4L;

    public Integer getX() {
        return x;
    }

    public Location(int x) {
        this.x = x;
    }
}
