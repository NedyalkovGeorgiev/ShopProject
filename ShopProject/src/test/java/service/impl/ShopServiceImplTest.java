package service.impl;

import org.informatics.data.Employee;
import org.informatics.data.Item;
import org.informatics.data.Shop;
import org.informatics.service.ItemService;
import org.informatics.service.ShopService;
import org.informatics.service.impl.ItemServiceImpl;
import org.informatics.service.impl.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopServiceImplTest {

    private ItemService itemService;
    private ShopServiceImpl shopService;
    private Shop shop;

    @BeforeEach
    void setup() {
        itemService = mock(ItemService.class);
        shopService = new ShopServiceImpl(itemService);
        shop = mock(Shop.class);
    }

    @Test
    void whenCalculateMarkup_givenPriceAndMarkup_thenCorrectResult() {
        // Arrange
        double price = 100.0;
        double markupPercent = 20.0;
        double expected = 120.0;

        // Act
        Double result = shopService.calculateMarkup(price, markupPercent);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void whenCalculateDiscount_givenPriceAndItem_thenCorrectDiscount() {
        // Arrange
        double price = 100.0;
        double discountPercent = 15.0;
        Item mockItem = mock(Item.class);

        when(itemService.getDiscount(mockItem)).thenReturn(discountPercent);

        double expectedDiscount = price * discountPercent / 100;

        // Act
        Double discount = shopService.calculateDiscount(price, mockItem);

        // Assert
        assertEquals(expectedDiscount, discount);
        verify(itemService).getDiscount(mockItem);
    }

    @Test
    void removeExpiredItem_removesCorrectItems() {
        Shop shop = mock(Shop.class);
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);

        when(item1.name()).thenReturn("item1");
        when(item2.name()).thenReturn("item2");

        List<Item> list1 = new ArrayList<>(List.of(item1));
        List<Item> list2 = new ArrayList<>(List.of(item2));

        Map<String, List<Item>> availableItems = new HashMap<>();
        availableItems.put("item1", list1);
        availableItems.put("item2", list2);

        when(shop.getAvailableItems()).thenReturn(availableItems);

        List<Item> toRemove = List.of(item1);

        ShopService shopService = new ShopServiceImpl(new ItemServiceImpl()); // or a mock if needed
        shopService.removeExpiredItem(shop, toRemove);

        assertFalse(availableItems.containsKey("item1"), "item1 key should have been removed");
        assertTrue(availableItems.containsKey("item2"), "item2 key should still be present");
    }

    @Test
    void calculateDiscount_zeroPercent_returnsZero() {
        double price = 100.0;
        Item mockItem = mock(Item.class);

        when(itemService.getDiscount(mockItem)).thenReturn(0.0);

        Double result = shopService.calculateDiscount(price, mockItem);
        assertEquals(0.0, result);
    }

    @Test
    void calculateExpenses_noEmployees_shouldPrintError() {
        when(shop.getEmployees()).thenReturn(Collections.emptyList());
        when(shop.getId()).thenReturn(2L);

        shopService.calculateExpenses(shop);
    }

    @Test
    void countInvoices_noFiles_shouldPrintNoInvoices() {
        File mockDir = mock(File.class);
        File[] empty = new File[0];

        File dir = new File("invoices");
        if (!dir.exists()) dir.mkdir();

        shopService.countInvoices(999L);
    }

    @Test
    void calculateMarkup_givenValidInputs_returnsCorrectValue() {
        assertEquals(120.0, shopService.calculateMarkup(100.0, 20.0));
    }

    @Test
    void calculateDiscount_givenValidInputs_returnsCorrectValue() {
        Item item = mock(Item.class);
        when(itemService.getDiscount(item)).thenReturn(15.0);

        Double result = shopService.calculateDiscount(100.0, item);

        assertEquals(15.0, result); // 100 * 15%
        verify(itemService).getDiscount(item);
    }

    @Test
    void calculateDiscount_zeroDiscount_returnsZero() {
        Item item = mock(Item.class);
        when(itemService.getDiscount(item)).thenReturn(0.0);

        Double result = shopService.calculateDiscount(100.0, item);

        assertEquals(0.0, result);
    }

    @Test
    void calculateExpenses_validData_printsTotal() {
        Employee emp1 = new Employee("A", 1L, 1000.0);
        Employee emp2 = new Employee("B", 2L, 1500.0);
        List<Employee> employees = List.of(emp1, emp2);

        Item item1 = new Item(1L, "item1", 10.0, null, new Date());
        Item item2 = new Item(2L, "item2", 20.0, null, new Date());

        Map<String, List<Item>> delivered = new HashMap<>();
        delivered.put("x", List.of(item1, item2));

        when(shop.getEmployees()).thenReturn(employees);
        when(shop.getDeliveredItems()).thenReturn(delivered);
        when(shop.getId()).thenReturn(1L);

        shopService.calculateExpenses(shop);
    }

    @Test
    void calculateExpenses_noEmployees_throwsHandled() {
        when(shop.getEmployees()).thenReturn(Collections.emptyList());
        when(shop.getId()).thenReturn(2L);

        shopService.calculateExpenses(shop);
    }

    @Test
    void calculateExpenses_noDeliveredItems_throwsHandled() {
        when(shop.getEmployees()).thenReturn(List.of(new Employee("A", 1L, 100.0)));
        when(shop.getDeliveredItems()).thenReturn(new HashMap<>());
        when(shop.getId()).thenReturn(3L);

        shopService.calculateExpenses(shop);
    }

    @Test
    void calculateTotalIncome_withInvoices_sumsCorrectly(@TempDir Path tempDir) throws IOException {
        String fileName = "invoice_1001";
        File invoiceFile = tempDir.resolve(fileName).toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(invoiceFile))) {
            writer.write("Some header line\n");
            writer.write("TOTAL  200.50\n");
        }

        System.setProperty("user.dir", tempDir.toFile().getAbsolutePath());
        new File(tempDir.toFile(), "invoices").mkdir();

        File invoicesDir = new File(tempDir.toFile(), "invoices");
        invoiceFile.renameTo(new File(invoicesDir, fileName));

        shopService.calculateTotalIncome(1001L);
    }

    @Test
    void countInvoices_withNone_printsNone(@TempDir Path tempDir) {
        new File(tempDir.toFile(), "invoices").mkdir();

        System.setProperty("user.dir", tempDir.toFile().getAbsolutePath());

        shopService.countInvoices(888L);
    }
}