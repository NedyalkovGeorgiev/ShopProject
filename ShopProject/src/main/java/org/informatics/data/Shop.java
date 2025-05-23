package org.informatics.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {
    private final long id;
    private final List<Employee> employees;
    private final Map<String, List<Item>> deliveredItems = new HashMap<>();
    private final Map<String, List<Item>> availableItems = new HashMap<>();
    private final Map<String, List<Item>> soldItems = new HashMap<>();
    private final Double markup;

    public Shop(long id, List<Employee> employees, Double markup) {
        this.id = id;
        this.employees = employees;
        this.markup = markup;
    }

    public Map<String, List<Item>> getDeliveredItems() {
        return deliveredItems;
    }

    public Map<String, List<Item>> getAvailableItems() {
        return availableItems;
    }

    public Map<String, List<Item>> getSoldItems() {
        return soldItems;
    }

    public void addToDeliveredItems(Map<String, List<Item>> deliveredItems) {
        for (String itemName : deliveredItems.keySet()) {
            this.deliveredItems.compute(itemName, (key, existingList) -> {
                if (existingList == null) {
                    return new ArrayList<>(deliveredItems.get(itemName));
                } else {
                    existingList.addAll(deliveredItems.get(itemName));
                    return existingList;
                }
            });
        }
    }

    public void addToAvailableItems(Map<String, List<Item>> deliveredItems) {
        for (String itemName : deliveredItems.keySet()) {
            this.availableItems.compute(itemName, (key, existingList) -> {
                if (existingList == null) {
                    return new ArrayList<>(deliveredItems.get(itemName));
                } else {
                    existingList.addAll(deliveredItems.get(itemName));
                    return existingList;
                }
            });
        }
    }

    public void addToSoldItems(Map<String, List<Item>> soldItems) {
        for (String itemName : soldItems.keySet()) {
            this.soldItems.compute(itemName, (key, existingList) -> {
                if (existingList == null) {
                    return new ArrayList<>(soldItems.get(itemName));
                } else {
                    existingList.addAll(soldItems.get(itemName));
                    return existingList;
                }
            });
        }
    }

    public Double getMarkup() {
        return markup;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public long getId() {
        return id;
    }
}
