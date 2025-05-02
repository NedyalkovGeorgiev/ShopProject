package org.informatics.service;

import org.informatics.data.InvoiceEntity;
import org.informatics.data.ShopEntity;

public interface InvoiceService {
    InvoiceEntity createInvoice(ShopEntity shopEntity);
}
