package com.switchcase.renting.service.model;

import com.switchcase.renting.service.database.item.ItemDetailsDB;
import com.switchcase.renting.service.user.User;
import com.switchcase.renting.service.util.Item;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

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

    public void searchItem(Item requiredItem, Properties serviceProperty) throws IOException, ClassNotFoundException {
        ItemDetailsDB itemDetailsDB = ItemDetailsDB.getInstance(serviceProperty);
        for (var entry : itemDetailsDB.getItems().entrySet()) {
            if (isRequired(entry, requiredItem)) {
                print(entry);
            }
        }
    }

    private void print(Map.Entry<String, Item> entry) {
        Item item = entry.getValue();
        item.print(entry.getKey());
    }

    private boolean isRequired(Map.Entry<String, Item> entry, Item requiredItem) {
        if (requiredItem.getTitle() != null && !requiredItem.getTitle().equals(entry.getValue().getTitle())) {
            return false;
        }

        if (requiredItem.getProducers() != null
            && !entry.getValue().getProducers().containsAll(requiredItem.getProducers())) {
            return false;
        }
        return true;
    }
}
