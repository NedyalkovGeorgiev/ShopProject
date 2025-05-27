package service.impl;

import org.informatics.data.Employee;
import org.informatics.data.Invoice;
import org.informatics.data.Item;
import org.informatics.data.Shop;
import org.informatics.service.ShopService;
import org.informatics.utils.fileio.InvoiceFileIOUtil;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceFileIOUtilTest {

    private ShopService shopService;
    private InvoiceFileIOUtil fileIOUtil;
    private Shop mockShop;
    private Item item1, item2;
    private Invoice invoice;
    private final String invoiceDir = "invoices";

    @BeforeEach
    void setup() {
        shopService = mock(ShopService.class);
        fileIOUtil = new InvoiceFileIOUtil(shopService);

        mockShop = mock(Shop.class);
        when(mockShop.getId()).thenReturn(1L);
        when(mockShop.getMarkup()).thenReturn(10.0);

        item1 = new Item(1L, "Apple", 2.0, null, new Date());
        item2 = new Item(2L, "Apple", 2.0, null, new Date());

        Map<String, List<Item>> itemMap = new HashMap<>();
        itemMap.put("Apple", List.of(item1, item2));

        Employee emp = new Employee("Alice", 101L, 2500.0);
        invoice = new Invoice(1001L, emp, new Date(), 3.6, itemMap);

        when(shopService.calculateMarkup(2.0, 10.0)).thenReturn(2.2);
        when(shopService.calculateDiscount(2.0, item1)).thenReturn(0.5);
        when(shopService.calculateDiscount(2.0, item2)).thenReturn(0.5);
    }

    @Test
    void write_createsInvoiceFileWithFormattedContent() throws IOException {
        fileIOUtil.write(invoice, mockShop);

        String fileName = invoiceDir + "/Invoice1001_1";
        File file = new File(fileName);

        assertTrue(file.exists(), "Invoice file should be created");

        String content = Files.readString(file.toPath());

        assertTrue(content.contains("INVOICE"));
        assertTrue(content.contains("Invoice ID : 1001"));
        assertTrue(content.contains("Apple"));
        assertTrue(content.contains("TOTAL:"));

        file.delete();
    }

    @Test
    void read_readsPreviouslyWrittenInvoiceContent() throws IOException {
        fileIOUtil.write(invoice, mockShop);

        String fileName = "Invoice1001";
        String readContent = fileIOUtil.read(fileName, 1L);

        assertNotNull(readContent);
        assertTrue(readContent.contains("Invoice ID : 1001"));
        assertTrue(readContent.contains("Alice"));
        assertTrue(readContent.contains("Apple"));

        new File(invoiceDir + "/Invoice1001_1").delete();
    }
}
