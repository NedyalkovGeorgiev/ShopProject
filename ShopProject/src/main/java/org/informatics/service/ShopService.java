package org.informatics.service;

import org.informatics.data.Item;
import org.informatics.data.Shop;
import java.util.List;

public interface ShopService {
    void removeExpiredItem(Shop shop, List<Item> items);
    void countInvoices(long shopId);
    void calculateTotalIncome(long shopId);
    void calculateExpenses(Shop shop);
    Double calculateMarkup(Double price, Double markup);
    Double calculateDiscount(Double price, Item item);
}
