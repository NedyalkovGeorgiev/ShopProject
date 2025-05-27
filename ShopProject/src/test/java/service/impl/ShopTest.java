package service.impl;

import org.informatics.data.Employee;
import org.informatics.data.Item;
import org.informatics.data.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    private Shop shop;
    private Item item1;
    private Item item2;

    @BeforeEach
    void setUp() {
        List<Employee> employees = List.of(new Employee("John", 1L, 2000.0));
        shop = new Shop(123L, employees, 15.0);

        item1 = new Item(1L, "item1", 10.0, null, new Date());
        item2 = new Item(2L, "item2", 20.0, null, new Date());
    }

    @Test
    void addToDeliveredItems_shouldMergeCorrectly() {
        Map<String, List<Item>> items = new HashMap<>();
        items.put("item1", new ArrayList<>(List.of(item1)));

        shop.addToDeliveredItems(items);

        assertTrue(shop.getDeliveredItems().containsKey("item1"));
        assertEquals(1, shop.getDeliveredItems().get("item1").size());
    }

    @Test
    void addToAvailableItems_shouldMergeCorrectly() {
        Map<String, List<Item>> items = new HashMap<>();
        items.put("item2", new ArrayList<>(List.of(item2)));

        shop.addToAvailableItems(items);

        assertTrue(shop.getAvailableItems().containsKey("item2"));
        assertEquals(1, shop.getAvailableItems().get("item2").size());
    }

    @Test
    void addToSoldItems_shouldMergeCorrectly() {
        Map<String, List<Item>> items = new HashMap<>();
        items.put("item1", new ArrayList<>(List.of(item1)));

        shop.addToSoldItems(items);

        assertTrue(shop.getSoldItems().containsKey("item1"));
        assertEquals(1, shop.getSoldItems().get("item1").size());
    }

    @Test
    void shopPropertiesShouldBeCorrect() {
        assertEquals(123L, shop.getId());
        assertEquals(15.0, shop.getMarkup());
        assertEquals(1, shop.getEmployees().size());
    }
}
