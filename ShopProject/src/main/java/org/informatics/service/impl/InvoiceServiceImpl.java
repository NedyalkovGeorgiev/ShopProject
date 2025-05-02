package org.informatics.service.impl;

import org.informatics.data.InvoiceEntity;
import org.informatics.data.ShopEntity;
import org.informatics.service.InvoiceService;
import org.informatics.service.InvoiceSequenceService;

public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceSequenceService invoiceSequenceService;

    public InvoiceServiceImpl(InvoiceSequenceService invoiceSequenceService) {
        this.invoiceSequenceService = invoiceSequenceService;
    }

    @Override
    public InvoiceEntity createInvoice(ShopEntity shopEntity) {
        return new InvoiceEntity(invoiceSequenceService.incrementInvoiceSequenceId(shopEntity));
    }
}
