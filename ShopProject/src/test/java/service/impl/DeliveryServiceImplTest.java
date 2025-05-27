package service.impl;

import org.informatics.data.Item;
import org.informatics.data.Shop;
import org.informatics.service.DeliveryService;
import org.informatics.service.impl.DeliveryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;

class DeliveryServiceImplTest {

    private DeliveryService deliveryService;
    private Shop shop;

    @BeforeEach
    void setUp() {
        deliveryService = new DeliveryServiceImpl();
        shop = mock(Shop.class);
    }

    @Test
    void delivery_addsItemsGroupedByNameToShop() {
        // Arrange
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);
        Item item3 = mock(Item.class);

        when(item1.name()).thenReturn("Milk");
        when(item2.name()).thenReturn("Milk");
        when(item3.name()).thenReturn("Cheese");

        List<Item> deliveredItems = List.of(item1, item2, item3);

        // Act
        deliveryService.delivery(deliveredItems, shop);

        Map<String, List<Item>> expectedGroupedItems = new HashMap<>();
        expectedGroupedItems.put("Milk", new ArrayList<>(List.of(item1, item2)));
        expectedGroupedItems.put("Cheese", new ArrayList<>(List.of(item3)));

        verify(shop).addToDeliveredItems(expectedGroupedItems);
        verify(shop).addToAvailableItems(expectedGroupedItems);
    }
}