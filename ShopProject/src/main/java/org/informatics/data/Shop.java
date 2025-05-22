package org.informatics.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {

    private final long id;
    private final List<Employee> employees;
    private Map<Item, Integer> deliveredItems = new HashMap<>();
    private Map<Item, Integer> availableItems = new HashMap<>();
    private Map<Item, Integer> soldItems = new HashMap<>();
    private final Double markup;

    public Shop(long id, List<Employee> employees, Double markup) {
        this.id = id;
        this.employees = employees;
        this.markup = markup;
    }

    public Map<Item, Integer> getDeliveredItems() {
        return deliveredItems;
    }

    public Map<Item, Integer> getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(Map<Item, Integer> availableItems) {
        this.availableItems = availableItems;
    }

    public Map<Item, Integer> getSoldItems() {
        return soldItems;
    }

    public void addToSoldItems(Map<Item, Integer> soldItems) {
        for (Item item : soldItems.keySet()) {
            Integer quantityOfSoldItemInShop = this.soldItems.get(item);
            Integer quantity = soldItems.get(item);

            this.getDeliveredItems().compute(item, (key, value) -> quantityOfSoldItemInShop == null ? quantity : quantityOfSoldItemInShop + quantity);
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
