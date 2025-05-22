package org.informatics.data;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public record Invoice(
        Long id,
        Employee employee,
        Date date,
        Double totalPrice,
        Map<Item, Integer> invoiceItemData
) implements Serializable {

    @NotNull
    @Override
    public String toString() {
        StringBuilder invoiceToPrint = new StringBuilder();

        String headerFormat = "%-20s %5s %10s %12s\n";
        String rowFormat = "%-20s %5d %10.2f %12.2f\n";

        StringBuilder temp = new StringBuilder();
        temp.append(String.format(headerFormat, "Item", "Qty", "Unit Price", "Total"));
        for (Item item : invoiceItemData.keySet()) {
            int quantity = invoiceItemData.get(item);
            double price = item.price();
            double itemTotal = price * quantity;
            temp.append(String.format(rowFormat, item.name(), quantity, price, itemTotal));
        }
        String[] lines = temp.toString().split("\n");
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
        invoiceToPrint.append("Invoice ID : ").append(id).append("\n");
        invoiceToPrint.append("Date       : ").append(date).append("\n");
        invoiceToPrint.append("Employee   : ").append(employee.name())
                .append(" (ID: ").append(employee.id()).append(")\n\n");

        invoiceToPrint.append(String.format(headerFormat, "Item", "Qty", "Unit Price", "Total"));
        invoiceToPrint.append(dashLine).append("\n");

        for (Item item : invoiceItemData.keySet()) {
            int quantity = invoiceItemData.get(item);
            double price = item.price();
            double itemTotal = price * quantity;
            invoiceToPrint.append(String.format(rowFormat, item.name(), quantity, price, itemTotal));
        }

        invoiceToPrint.append(dashLine).append("\n");
        invoiceToPrint.append(String.format("%-37s %10.2f\n", "TOTAL:", totalPrice));
        invoiceToPrint.append(equalLine).append("\n");

        return invoiceToPrint.toString();
    }
}
