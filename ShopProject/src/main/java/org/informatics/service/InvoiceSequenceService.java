package org.informatics.service;

import org.informatics.data.ShopEntity;

public interface InvoiceSequenceService {
    Long incrementInvoiceSequenceId(ShopEntity shopEntity);
}
