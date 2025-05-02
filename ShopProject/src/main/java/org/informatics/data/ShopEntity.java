package org.informatics.data;

import java.util.List;
import java.util.Map;

public class ShopEntity {
    private List<EmployeeEntity> employeeEntities;
    private Map<ItemEntity, Integer> deliveredItems;
    private Map<ItemEntity, Integer> availableItems;
    private Map<ItemEntity, Integer> soldItems;
    private Double markup;

    public Map<ItemEntity, Integer> getDeliveredItems() {
        return deliveredItems;
    }

    public void setDeliveredItems(Map<ItemEntity, Integer> deliveredItems) {
        this.deliveredItems = deliveredItems;
    }

    public Map<ItemEntity, Integer> getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(Map<ItemEntity, Integer> availableItems) {
        this.availableItems = availableItems;
    }

    public Map<ItemEntity, Integer> getSoldItems() {
        return soldItems;
    }

    public void setSoldItems(Map<ItemEntity, Integer> soldItems) {
        this.soldItems = soldItems;
    }

    public Double getMarkup() {
        return markup;
    }

    public void setMarkup(Double markup) {
        this.markup = markup;
    }

    public List<EmployeeEntity> getEmployeeEntities() {
        return employeeEntities;
    }

    public void setEmployeeEntities(List<EmployeeEntity> employeeEntities) {
        this.employeeEntities = employeeEntities;
    }
}
