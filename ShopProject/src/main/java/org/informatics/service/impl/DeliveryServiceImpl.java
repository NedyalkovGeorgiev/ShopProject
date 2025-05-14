package org.informatics.service.impl;

import org.informatics.data.Item;
import org.informatics.data.Shop;
import org.informatics.service.DeliveryService;

import java.util.Map;

public class DeliveryServiceImpl implements DeliveryService {

    @Override
    public void delivery(Map<Item, Integer> deliveredItems, Shop shop) {
        for (Item item : deliveredItems.keySet()) {
            updateDeliveredItems(item, deliveredItems.get(item), shop);
            updateAvailableItems(item, deliveredItems.get(item), shop);
        }
    }

    private void updateDeliveredItems(Item item, Integer quantity, Shop shop) {
        shop.getDeliveredItems().put(item, shop.getDeliveredItems().get(item) + quantity);
    }

    private void updateAvailableItems(Item item, Integer quantity, Shop shop) {
        shop.getAvailableItems().put(item, shop.getAvailableItems().get(item) + quantity);
    }
}
