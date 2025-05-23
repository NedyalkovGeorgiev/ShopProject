package org.informatics.service.impl;

import org.informatics.data.Employee;
import org.informatics.data.Invoice;
import org.informatics.data.Item;
import org.informatics.data.Shop;
import org.informatics.service.InvoiceService;
import org.informatics.service.InvoiceSequenceService;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceSequenceService invoiceSequenceService;

    public InvoiceServiceImpl(InvoiceSequenceService invoiceSequenceService) {
        this.invoiceSequenceService = invoiceSequenceService;
    }

    @Override
    public Invoice createInvoice(Shop shop, Employee employee, Date date, Double totalPrice, Map<String, List<Item>> items) {
        return new Invoice(invoiceSequenceService.incrementInvoiceSequenceId(shop), employee, date, totalPrice, items);
    }
}
