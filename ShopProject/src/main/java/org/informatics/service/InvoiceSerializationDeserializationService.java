package org.informatics.service;

import org.informatics.data.Invoice;

public interface InvoiceSerializationDeserializationService {
    void serializeInvoice(String filename, Invoice invoice);
    Invoice deserializeInvoice(String fileName);
}
