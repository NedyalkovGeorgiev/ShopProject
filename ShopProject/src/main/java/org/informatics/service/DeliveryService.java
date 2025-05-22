package org.informatics.service;

import org.informatics.data.Item;
import org.informatics.data.Shop;
import java.util.Map;

public interface DeliveryService {
    void delivery(Map<Item, Integer> deliveredItems, Shop shop);
}
