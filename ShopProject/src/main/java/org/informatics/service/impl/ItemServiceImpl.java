package org.informatics.service.impl;

import org.informatics.data.Category;
import org.informatics.data.Item;
import org.informatics.service.ItemService;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.informatics.Constants.*;

public class ItemServiceImpl implements ItemService {
    @Override
    public Double getDiscount(Item item) {
        if (Category.NON_FOOD.equals(item.category())) {
            return NO_DISCOUNT;
        }

        LocalDate expiryDateLocalDate = item.expiryDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (LocalDate.now().isAfter(expiryDateLocalDate.minusMonths(PERIOD_BEFORE_DISCOUNT))) {
            return PRICE_DISCOUNT;
        } else {
            return NO_DISCOUNT;
        }
    }
}
