package org.informatics.service;

import org.informatics.data.ItemEntity;
import org.informatics.data.ShopEntity;

import java.util.Map;

public interface DeliveryService {
    void delivery(Map<ItemEntity, Integer> deliveredItems, ShopEntity shopEntity);
}
