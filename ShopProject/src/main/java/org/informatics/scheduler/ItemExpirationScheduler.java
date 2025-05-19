package org.informatics.scheduler;

import org.informatics.data.Item;
import org.informatics.data.Shop;
import org.informatics.service.ShopService;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ItemExpirationScheduler {
    private final Shop shop;
    private final ShopService shopService;
    private final Long timeBetweenCheck;
    private final ScheduledExecutorService scheduledExecutorService;

    public ItemExpirationScheduler(Shop shop, ShopService shopService, Long intervalBetweenCheck, ScheduledExecutorService scheduledExecutorService) {
        this.shop = shop;
        this.shopService = shopService;
        this.timeBetweenCheck = intervalBetweenCheck;
        this.scheduledExecutorService = scheduledExecutorService;

        scheduleItemExpiration();
    }

    public void scheduleItemExpiration() {
        scheduledExecutorService.scheduleAtFixedRate(this::checkItemExpiration, 0, timeBetweenCheck, TimeUnit.DAYS);
    }

    private void checkItemExpiration() {
        System.out.println("This got printed at: " + new Date());
        List<Item> itemsToRemove = new ArrayList<>();

        for (Item item : shop.getAvailableItems().keySet()) {
            if (new Date().after(item.getExpiryDate())) {
                itemsToRemove.add(item);
            }
        }

        shopService.removeExpiredItem(itemsToRemove);
    }
}
