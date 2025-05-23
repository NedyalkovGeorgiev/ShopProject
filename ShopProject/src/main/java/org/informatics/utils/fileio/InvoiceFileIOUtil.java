package org.informatics.utils.fileio;

import org.informatics.data.Invoice;
import org.informatics.data.Item;
import org.informatics.data.Shop;
import org.informatics.service.ShopService;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.informatics.Constants.*;

public class InvoiceFileIOUtil extends FileIOUtil {
    private final ShopService shopService;

    public InvoiceFileIOUtil(ShopService shopService) {
        this.shopService = shopService;
    }

    public String read(String fileName, long shopId) throws IOException {
        return super.read(INVOICE_DIRECTORY_NAME, fileName, shopId);
    }

    public void write(Invoice invoice, Shop shop) throws IOException {
        String fileName = INVOICE_FILE_NAME + invoice.id() + INVOICE_FILE_NAME_DELIMITER + shop.getId();

        super.write(INVOICE_DIRECTORY_NAME, fileName, convertInvoiceToString(invoice, shop.getMarkup()));
    }

    private String convertInvoiceToString(Invoice invoice, Double markup) {
        StringBuilder invoiceToPrint = new StringBuilder();

        String headerFormat = "%-20s %5s %10s %12s\n";
        String rowFormat = "%-20s %5d %10.2f %12.2f\n";

        StringBuilder linesLengthString = new StringBuilder();
        linesLengthString.append(String.format(headerFormat, "Item", "Qty", "Unit Price", "Total"));

        StringBuilder invoiceLines = getInvoiceLines(rowFormat, invoice.invoiceItemData(), markup);
        linesLengthString.append(invoiceLines);

        String[] lines = linesLengthString.toString().split("\n");
        int maxLineLength = 0;

        for (String line : lines) {
            maxLineLength = Math.max(maxLineLength, line.length());
        }

        String equalLine = "=".repeat(maxLineLength);
        String dashLine = "-".repeat(maxLineLength);

        String title = "INVOICE";
        int padding = (maxLineLength - title.length()) / 2;
        String centeredTitle = "=".repeat(padding) + " " + title + " " + "=".repeat(maxLineLength - padding - title.length() - 2);

        invoiceToPrint.append(centeredTitle).append("\n");
        invoiceToPrint.append("Invoice ID : ").append(invoice.id()).append("\n");
        invoiceToPrint.append("Date       : ").append(invoice.date()).append("\n");
        invoiceToPrint.append("Employee   : ").append(invoice.employee().name())
                .append(" (ID: ").append(invoice.employee().id()).append(")\n\n");
        invoiceToPrint.append(String.format(headerFormat, "Item", "Qty", "Unit Price", "Total"));
        invoiceToPrint.append(dashLine).append("\n");
        invoiceToPrint.append(invoiceLines);
        invoiceToPrint.append(dashLine).append("\n");
        invoiceToPrint.append(String.format("%-37s %10.2f\n", "TOTAL:", invoice.totalPrice()));
        invoiceToPrint.append(equalLine).append("\n");

        return invoiceToPrint.toString();
    }

    private StringBuilder getInvoiceLines(String rowFormat, Map<String, List<Item>> invoiceItemData, Double markup) {
        StringBuilder invoiceLine = new StringBuilder();

        for (String itemName : invoiceItemData.keySet()) {
            List<Item> itemsToRecordInInvoice = invoiceItemData.get(itemName);
            int quantity = itemsToRecordInInvoice.size();
            double price = 0;
            double itemTotal = 0;

            for (Item item : itemsToRecordInInvoice) {
                price = calculateItemPrice(item, markup);
                itemTotal += price;
            }

            invoiceLine.append(String.format(rowFormat, itemName, quantity, price, itemTotal));
        }

        return invoiceLine;
    }

    private Double calculateItemPrice(Item item, Double markup) {
        double price = item.price();

        return shopService.calculateMarkup(price, markup) - shopService.calculateDiscount(price, item);
    }
}
