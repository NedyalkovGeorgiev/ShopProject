package org.informatics.utils.fileio;

import org.informatics.data.Invoice;
import java.io.IOException;

import static org.informatics.Constants.*;

public class InvoiceFileIOUtil extends FileIOUtil<Invoice> {
    public String read(String fileName, long shopId) throws IOException {
        return super.read(INVOICE_DIRECTORY_NAME, fileName, shopId);
    }

    public void write(Invoice invoice, long shopId) throws IOException {
        String fileName = INVOICE_FILE_NAME + invoice.id() + INVOICE_FILE_NAME_DELIMITER + shopId;

        super.write(INVOICE_DIRECTORY_NAME, fileName, invoice);
    }
}
