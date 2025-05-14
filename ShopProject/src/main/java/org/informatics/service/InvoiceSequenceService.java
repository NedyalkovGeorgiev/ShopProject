package org.informatics.service;

import org.informatics.data.Shop;

public interface InvoiceSequenceService {
    Long incrementInvoiceSequenceId(Shop shop);
}
