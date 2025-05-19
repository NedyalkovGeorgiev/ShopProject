package org.informatics.utils.fileio;

import org.informatics.data.Invoice;

import java.io.FileNotFoundException;
import java.io.IOException;

public class InvoiceFileIOUtil extends FileIOUtil<Invoice> {
    public String read(String fileName) throws FileNotFoundException, IOException {
        return super.read(fileName);
    }

    public Invoice write(Invoice invoice) throws FileNotFoundException, IOException {
        String fileName = "Invoice" + invoice.id();

        return super.write(fileName, invoice);
    }
}
