package service.impl;

import org.informatics.data.Employee;
import org.informatics.data.Invoice;
import org.informatics.data.Item;
import org.informatics.data.Shop;
import org.informatics.service.InvoiceSequenceService;
import org.informatics.service.impl.InvoiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceServiceImplTest {

    private InvoiceSequenceService invoiceSequenceService;
    private InvoiceServiceImpl invoiceService;

    @BeforeEach
    void setup() {
        invoiceSequenceService = mock(InvoiceSequenceService.class);
        invoiceService = new InvoiceServiceImpl(invoiceSequenceService);
    }

    @Test
    void createInvoice_createsInvoiceWithCorrectData() {
        // Arrange
        Shop mockShop = mock(Shop.class);
        Employee mockEmployee = new Employee("Alice", 1L, 1200.0);
        Date date = new Date();
        double totalPrice = 100.0;

        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);

        Map<String, List<Item>> items = new HashMap<>();
        items.put("Milk", List.of(item1, item2));

        Long expectedInvoiceId = 5L;
        when(invoiceSequenceService.incrementInvoiceSequenceId(mockShop)).thenReturn(expectedInvoiceId);

        // Act
        Invoice invoice = invoiceService.createInvoice(mockShop, mockEmployee, date, totalPrice, items);

        // Assert
        assertNotNull(invoice);
        assertEquals(expectedInvoiceId, invoice.id());
        assertEquals(mockEmployee, invoice.employee());
        assertEquals(date, invoice.date());
        assertEquals(totalPrice, invoice.totalPrice());
        assertEquals(items, invoice.invoiceItemData());

        verify(invoiceSequenceService).incrementInvoiceSequenceId(mockShop);
    }
}