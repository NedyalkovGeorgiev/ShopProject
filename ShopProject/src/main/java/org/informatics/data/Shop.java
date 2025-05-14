package org.informatics.data;

import java.util.List;
import java.util.Map;

public class Shop {
    //TODO: Shops need to have name (or at least Id) as well, because different shops start their invoicing from 1.
    // This means, that for 2 different shops the same Invoice will be generated: Invoice1 !!!!PROBLEM!!!!

    private List<Employee> employees;
    private Map<Item, Integer> deliveredItems;
    private Map<Item, Integer> availableItems;
    private Map<Item, Integer> soldItems;
    private Double markup;

    //TODO: needs more logic for those
    private Long invoiceCount;
    private Double totalIncome;

    public Map<Item, Integer> getDeliveredItems() {
        return deliveredItems;
    }

    public void setDeliveredItems(Map<Item, Integer> deliveredItems) {
        this.deliveredItems = deliveredItems;
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

    public void setSoldItems(Map<Item, Integer> soldItems) {
        this.soldItems = soldItems;
    }

    public Double getMarkup() {
        return markup;
    }

    public void setMarkup(Double markup) {
        this.markup = markup;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
