package org.informatics.data;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Map;

public record Invoice(
        Long id,
        Employee employee,
        Date date,
        Double totalPrice,
        Map<Item, Integer> invoiceItemData
) {

    @NotNull
    @Override
    public String toString() {
        StringBuilder invoiceToPrint = new StringBuilder();
        invoiceToPrint.append("Invoice: ").append(id).append("\n");
        invoiceToPrint.append("Date: ").append(date).append("\n");
        invoiceToPrint.append("Employee: ").append(employee.getName()).append(" #").append(employee.getId()).append("\n");

        for (Item item : invoiceItemData.keySet()) {
            Integer quantity = invoiceItemData.get(item);
            Double price = item.getPrice();

            invoiceToPrint.append(item.getName()).append(" ")
                    .append(quantity).append(" x ").append(price).append(": ").append(price * quantity).append("\n");
        }

        invoiceToPrint.append("Total Price: ").append(totalPrice).append("\n");

        return invoiceToPrint.toString();
    }
}
