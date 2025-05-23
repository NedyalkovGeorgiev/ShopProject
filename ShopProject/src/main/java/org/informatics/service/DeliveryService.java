package org.informatics.service;

import org.informatics.data.Item;
import org.informatics.data.Shop;

import java.util.List;

public interface DeliveryService {
    void delivery(List<Item> deliveredItems, Shop shop);
}
