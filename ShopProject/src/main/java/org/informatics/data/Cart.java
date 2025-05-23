package org.informatics.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private final Map<String, List<Item>> items = new HashMap<>();

    public Map<String, List<Item>> getItems() {
        return items;
    }
}
