package org.informatics.scheduler;

import org.informatics.data.Category;
import org.informatics.data.Item;
import org.informatics.data.Shop;
import org.informatics.service.ShopService;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.informatics.Constants.TIME_BETWEEN_CHECK;

public class ItemExpirationScheduler {
    private final Shop shop;
    private final ShopService shopService;
    private final ScheduledExecutorService scheduledExecutorService;

    public ItemExpirationScheduler(Shop shop, ShopService shopService, ScheduledExecutorService scheduledExecutorService) {
        this.shop = shop;
        this.shopService = shopService;
        this.scheduledExecutorService = scheduledExecutorService;

        scheduleItemExpiration();
    }

    private void scheduleItemExpiration() {
        scheduledExecutorService.scheduleAtFixedRate(this::checkItemExpiration, 0, TIME_BETWEEN_CHECK, TimeUnit.DAYS);
    }

    private void checkItemExpiration() {
        List<Item> itemsToRemove = new ArrayList<>();

        for (List<Item> items : shop.getAvailableItems().values()) {
            for (Item item : items) {
                if (Category.NON_FOOD.equals(item.category())) {
                    continue;
                }

                if (new Date().after(item.expiryDate())) {
                    itemsToRemove.add(item);
                }
            }
        }

        shopService.removeExpiredItem(shop, itemsToRemove);
    }

    public void shutdown() {
        scheduledExecutorService.shutdown();
    }
}
