package org.informatics.utils.fileio;

import org.informatics.data.Invoice;

public class InvoiceFileIOUtil extends FileIOUtil<Invoice> {
    public String read(String fileName) {
        return super.read(fileName);
    }

    public Invoice write(Invoice invoice) {
        String fileName = "Invoice" + invoice.id();

        return super.write(fileName, invoice);
    }
}
