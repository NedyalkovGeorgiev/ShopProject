package org.informatics.data;

import java.util.Map;

public class Cart {
    private Map<Item, Integer> items;

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }
}
