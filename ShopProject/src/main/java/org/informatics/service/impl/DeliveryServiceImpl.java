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
        Integer quantityOfItemInShop = shop.getDeliveredItems().get(item);

        shop.getDeliveredItems().compute(item, (key, value) -> quantityOfItemInShop == null ? quantity : quantityOfItemInShop + quantity);
    }

    private void updateAvailableItems(Item item, Integer quantity, Shop shop) {
        Integer quantityOfItemInShop = shop.getAvailableItems().get(item);

        shop.getAvailableItems().compute(item, (key, value) -> quantityOfItemInShop == null ? quantity : quantityOfItemInShop + quantity);
    }
}
