package service.impl;

import org.informatics.data.*;
import org.informatics.service.InvoiceService;
import org.informatics.service.ShopService;
import org.informatics.service.impl.ClientServiceImpl;
import org.informatics.utils.fileio.InvoiceFileIOUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    private InvoiceService invoiceService;
    private InvoiceFileIOUtil invoiceFileIOUtil;
    private ShopService shopService;
    private ClientServiceImpl clientService;

    private Shop shop;
    private Client client;
    private Cart cart;
    private Employee employee;
    private CashRegister cashRegister;
    private Item testItem;

    @BeforeEach
    void setUp() {
        invoiceService = mock(InvoiceService.class);
        invoiceFileIOUtil = mock(InvoiceFileIOUtil.class);
        shopService = mock(ShopService.class);

        clientService = new ClientServiceImpl(invoiceService, invoiceFileIOUtil, shopService);

        shop = new Shop(1L, new ArrayList<>(), 10.0);
        cart = new Cart();
        client = new Client();
        client.setCart(cart);
        client.setBudget(100.0);

        employee = new Employee("John", 1L, 2000.0);
        cashRegister = new CashRegister(shop);
        cashRegister.setEmployee(employee);

        testItem = new Item(1L, "TestItem", 20.0, Category.FOOD, new Date());
        List<Item> itemList = new ArrayList<>(List.of(testItem, testItem));
        Map<String, List<Item>> availableItems = new HashMap<>();
        availableItems.put("TestItem", itemList);
        shop.addToAvailableItems(availableItems);
    }

    @Test
    void testAddToCart_success() {
        clientService.addToCart(client, "TestItem", 1, shop);

        assertTrue(client.getCart().getItems().containsKey("TestItem"));
        assertEquals(1, client.getCart().getItems().get("TestItem").size());
        assertEquals(1, shop.getAvailableItems().get("TestItem").size());
    }

    @Test
    void testAddToCart_itemNotFound() {
        clientService.addToCart(client, "UnknownItem", 1, shop);
        assertTrue(client.getCart().getItems().isEmpty());
    }

    @Test
    void testAddToCart_notEnoughStock() {
        clientService.addToCart(client, "TestItem", 5, shop);
        assertTrue(client.getCart().getItems().isEmpty());
    }

    @Test
    void testPayAtCashRegister_success() throws IOException {
        clientService.addToCart(client, "TestItem", 1, shop);

        Invoice mockInvoice = new Invoice(1L, employee, new Date(), 22.0, cart.getItems());
        when(invoiceService.createInvoice(any(), any(), any(), anyDouble(), any())).thenReturn(mockInvoice);
        when(shopService.calculateMarkup(anyDouble(), anyDouble())).thenReturn(22.0);
        when(shopService.calculateDiscount(anyDouble(), any())).thenReturn(0.0);

        clientService.payAtCashRegister(cart, cashRegister, client);

        assertEquals(78.0, client.getBudget());
        verify(invoiceFileIOUtil, times(1)).write(any(), eq(shop));
        assertTrue(shop.getSoldItems().containsKey("TestItem"));
    }
}
