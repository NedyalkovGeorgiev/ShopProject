package service.impl;

import org.informatics.data.Shop;
import org.informatics.service.impl.InvoiceSequenceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.informatics.Constants.INVOICE_STARTING_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InvoiceSequenceServiceImplTest {

    private InvoiceSequenceServiceImpl sequenceService;

    @BeforeEach
    void setUp() {
        sequenceService = new InvoiceSequenceServiceImpl();
    }

    @Test
    void incrementInvoiceSequenceId_shouldStartFromDefaultAndIncrement() {
        Shop shop = mock(Shop.class);

        Long firstId = sequenceService.incrementInvoiceSequenceId(shop);
        Long secondId = sequenceService.incrementInvoiceSequenceId(shop);
        Long thirdId = sequenceService.incrementInvoiceSequenceId(shop);

        assertEquals(INVOICE_STARTING_ID, firstId, "First ID should match INVOICE_STARTING_ID");
        assertEquals(INVOICE_STARTING_ID + 1, secondId, "Second ID should increment by 1");
        assertEquals(INVOICE_STARTING_ID + 2, thirdId, "Third ID should increment by 2");
    }

    @Test
    void incrementInvoiceSequenceId_shouldTrackMultipleShopsIndependently() {
        Shop shop1 = mock(Shop.class);
        Shop shop2 = mock(Shop.class);

        Long shop1First = sequenceService.incrementInvoiceSequenceId(shop1);
        Long shop2First = sequenceService.incrementInvoiceSequenceId(shop2);
        Long shop1Second = sequenceService.incrementInvoiceSequenceId(shop1);

        assertEquals(INVOICE_STARTING_ID, shop1First);
        assertEquals(INVOICE_STARTING_ID, shop2First);
        assertEquals(INVOICE_STARTING_ID + 1, shop1Second);
    }
}