package com.switchcase.renting.service.model;

import com.switchcase.renting.service.user.User;
import com.switchcase.renting.service.util.Item;

// @Singleton
public class DisplayService {
    private static DisplayService obj;

    public static DisplayService getInstance() {
        if (obj == null) {
            obj = new DisplayService();
        }
        return obj;
    }

    public void showUserProfile(User user) {
    }

    public void searchItem(Item item) {

    }
}
