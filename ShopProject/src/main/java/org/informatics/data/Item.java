package org.informatics.data;

import java.util.Date;

public class Item {
    private final Long id;
    private final String name;
    private Double price;
    private final Category category;
    private final Date expiryDate;

    public Item(Long id, String name, Double price, Category category, Date expiryDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }
}
