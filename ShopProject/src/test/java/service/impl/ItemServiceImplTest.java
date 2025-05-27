package service.impl;

import org.informatics.data.Category;
import org.informatics.data.Item;
import org.informatics.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.informatics.Constants.NO_DISCOUNT;
import static org.informatics.Constants.PRICE_DISCOUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemServiceImplTest {

    private ItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        itemService = new ItemServiceImpl();
    }

    @Test
    void getDiscount_nonFoodItem_returnsNoDiscount() {
        Item item = new Item(1L, "Shampoo", 10.0, Category.NON_FOOD, addMonthsToDate(6));
        Double result = itemService.getDiscount(item);
        assertEquals(NO_DISCOUNT, result);
    }

    @Test
    void getDiscount_foodItemExpiringSoon_returnsPriceDiscount() {
        Item item = new Item(2L, "Milk", 5.0, Category.FOOD, addDaysToDate(20)); // within 1 month
        Double result = itemService.getDiscount(item);
        assertEquals(PRICE_DISCOUNT, result);
    }

    @Test
    void getDiscount_foodItemExpiringToday_returnsPriceDiscount() {
        Item item = new Item(3L, "Bread", 2.0, Category.FOOD, new Date()); // today
        Double result = itemService.getDiscount(item);
        assertEquals(PRICE_DISCOUNT, result);
    }

    @Test
    void getDiscount_foodItemExpiringLater_returnsNoDiscount() {
        Item item = new Item(4L, "Cheese", 6.0, Category.FOOD, addMonthsToDate(2)); // 2 months out
        Double result = itemService.getDiscount(item);
        assertEquals(NO_DISCOUNT, result);
    }

    private Date addMonthsToDate(int months) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    private Date addDaysToDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }
}