package org.informatics.service.impl;

import org.informatics.data.Item;
import org.informatics.data.Shop;
import org.informatics.service.DeliveryService;
import java.util.*;

public class DeliveryServiceImpl implements DeliveryService {

    @Override
    public void delivery(List<Item> deliveredItems, Shop shop) {
        Map<String, List<Item>> itemsToBeDelivered = new HashMap<>();

        for (Item item : deliveredItems) {
            itemsToBeDelivered.compute(item.name(), (key, existingList) -> {
                if (existingList == null) {
                    return new ArrayList<>(Collections.singleton(item));
                } else {
                    existingList.add(item);
                    return existingList;
                }
            });
        }

        shop.addToDeliveredItems(itemsToBeDelivered);
        shop.addToAvailableItems(itemsToBeDelivered);
    }
}
