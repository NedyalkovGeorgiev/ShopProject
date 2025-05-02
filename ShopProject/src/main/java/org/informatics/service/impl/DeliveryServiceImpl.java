package org.informatics.service.impl;

import org.informatics.data.ItemEntity;
import org.informatics.data.ShopEntity;
import org.informatics.service.DeliveryService;

import java.util.Map;

public class DeliveryServiceImpl implements DeliveryService {

    @Override
    public void delivery(Map<ItemEntity, Integer> deliveredItems, ShopEntity shopEntity) {
        for (ItemEntity itemEntity : deliveredItems.keySet()) {
            updateDeliveredItems(itemEntity, deliveredItems.get(itemEntity), shopEntity);
            updateAvailableItems(itemEntity, deliveredItems.get(itemEntity), shopEntity);
        }
    }

    private void updateDeliveredItems(ItemEntity itemEntity, Integer quantity, ShopEntity shopEntity) {
        shopEntity.getDeliveredItems().put(itemEntity, shopEntity.getDeliveredItems().get(itemEntity) + quantity);
    }

    private void updateAvailableItems(ItemEntity itemEntity, Integer quantity, ShopEntity shopEntity) {
        shopEntity.getAvailableItems().put(itemEntity, shopEntity.getAvailableItems().get(itemEntity) + quantity);
    }
}
