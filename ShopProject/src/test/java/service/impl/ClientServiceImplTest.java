//package service.impl;
//
//import org.informatics.data.Cart;
//import org.informatics.data.Category;
//import org.informatics.data.Client;
//import org.informatics.data.Item;
//import org.informatics.service.InvoiceService;
//import org.informatics.service.ItemService;
//import org.informatics.service.impl.ClientServiceImpl;
//import org.informatics.utils.fileio.InvoiceFileIOUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.exceptions.base.MockitoException;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class ClientServiceImplTest {
//    private InvoiceService invoiceService;
//    private ItemService itemService;
//    private InvoiceFileIOUtil invoiceFileIOUtil;
//
//    private ClientServiceImpl clientServiceImpl;
//
//    @BeforeEach
//    void setUp() {
//        invoiceService = Mockito.mock(InvoiceService.class);
//        itemService = Mockito.mock(ItemService.class);
//        invoiceFileIOUtil = Mockito.mock(InvoiceFileIOUtil.class);
//
//        clientServiceImpl = new ClientServiceImpl(invoiceService, itemService, invoiceFileIOUtil);
//    }
//
//    @Test
//    void dse() {
//        Map<Item, Integer> items = mockListOfItemsWithOneItem();
//
//        Cart cart = new Cart();
//        cart.setItems(items);
//
//        Client client = new Client();
//        client.setCart(cart);
//
//        Item itemToAddToCart = (Item) items.keySet().toArray()[0];
//        Integer quantity = 1;
//
//        clientServiceImpl.addToCart(client, itemToAddToCart, quantity);
//        assertEquals(cart.getItems().get(itemToAddToCart) + quantity, client.getCart().getItems().get(itemToAddToCart));
//    }
//
//    private Map<Item, Integer> mockListOfItemsWithOneItem() {
//        Item item = mockItem();
//        Integer quantity = 1;
//
//        Map<Item, Integer> items = new HashMap<>();
//        items.put(item, quantity);
//
//        return items;
//    }
//
//    private Item mockItem() {
//        return new Item(1L, "TestItem", 1.0D, Category.FOOD, new Date());
//    }
//
//}
