package service.impl;

import org.informatics.data.Item;
import org.informatics.service.ItemService;
import org.informatics.service.impl.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceImplTest {

    private ItemService itemService;
    private ShopServiceImpl shopService;

    @BeforeEach
    void setup() {
        itemService = Mockito.mock(ItemService.class);
        shopService = new ShopServiceImpl(itemService);
    }

    @Test
    void whenCalculateMarkup_givenPriceAndMarkup_thenCorrectResult() {
        // Arrange
        double price = 100.0;
        double markupPercent = 20.0; // 20%
        double expected = 120.0; // price + 20%

        // Act
        Double result = shopService.calculateMarkup(price, markupPercent);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void whenCalculateDiscount_givenPriceAndItem_thenCorrectDiscount() {
        // Arrange
        double price = 100.0;
        double discountPercent = 15.0; // 15%
        Item mockItem = Mockito.mock(Item.class);

        Mockito.when(itemService.getDiscount(mockItem)).thenReturn(discountPercent);

        double expectedDiscount = price * discountPercent / 100;

        // Act
        Double discount = shopService.calculateDiscount(price, mockItem);

        // Assert
        assertEquals(expectedDiscount, discount);
        Mockito.verify(itemService).getDiscount(mockItem);
    }
}
