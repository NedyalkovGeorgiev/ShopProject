package org.informatics.service;

import org.informatics.data.Employee;
import org.informatics.data.Invoice;
import org.informatics.data.Item;
import org.informatics.data.Shop;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InvoiceService {
    Invoice createInvoice(Shop shop, Employee employee, Date date, Double totalPrice, Map<String, List<Item>> items);
}
