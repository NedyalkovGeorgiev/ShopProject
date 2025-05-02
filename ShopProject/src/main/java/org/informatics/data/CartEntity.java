package org.informatics.data;

import java.util.Map;

public class CartEntity {
    private Map<ItemEntity, Integer> items;

    public Map<ItemEntity, Integer> getItems() {
        return items;
    }

    public void setItems(Map<ItemEntity, Integer> items) {
        this.items = items;
    }
}
