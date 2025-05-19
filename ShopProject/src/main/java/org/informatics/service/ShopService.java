package org.informatics.service;

import org.informatics.data.Item;

import java.util.List;

public interface ShopService {
    void removeExpiredItem(List<Item> items);
    void countInvoices();
}
