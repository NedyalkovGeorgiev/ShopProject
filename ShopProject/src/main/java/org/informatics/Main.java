package org.informatics;

import org.informatics.data.*;
import org.informatics.service.InvoiceService;
import org.informatics.service.impl.InvoiceSequenceServiceImpl;
import org.informatics.service.impl.InvoiceServiceImpl;
import org.informatics.utils.fileio.InvoiceFileIOUtil;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Error Handling
        //Tests
        //Expiry date -> if expired DO NOT SELL!
        //Scheduler to check if something expired
        //Populate Main class/method
        //beautify the design of the Invoice
        //eventually? check if new directory is needed where the invoices will be created


        InvoiceService invoiceService = new InvoiceServiceImpl(new InvoiceSequenceServiceImpl());

        Shop shop = new Shop();
        shop.setMarkup(20D);
        Shop shop2 = new Shop();
        shop2.setMarkup(10D);
        Employee employee = new Employee("Johnathan", 1L, 900D);
        Date date = new Date();
        Double totalPrice = 420.69D;
        Map<Item, Integer> items = new HashMap<>();
        items.put(new Item(1L, "Kashkaval", 13.70D, Category.FOOD, new Date()), 5);
        items.put(new Item(2L, "Halva", 6.20D, Category.FOOD, new GregorianCalendar(2026, Calendar.MARCH, 27).getTime()), 3);
        items.put(new Item(3L, "Chorapi", 5.70D, Category.NON_FOOD, new GregorianCalendar(5078, Calendar.FEBRUARY, 11).getTime()), 10);
        items.put(new Item(4L, "Meso", 24.35D, Category.FOOD, new GregorianCalendar(2025, Calendar.MAY, 20).getTime()), 4);
        items.put(new Item(5L, "Voda", 1.50D,  Category.FOOD, new GregorianCalendar(2028, Calendar.JANUARY, 1).getTime()), 20);


        Invoice invoice = invoiceService.createInvoice(shop, employee, date, totalPrice, items);
        Invoice invoice1 = invoiceService.createInvoice(shop, employee, date, totalPrice, items);
        Invoice invoice2 = invoiceService.createInvoice(shop2, employee, date, totalPrice, items);

        InvoiceFileIOUtil invoiceFileIOUtil = new InvoiceFileIOUtil();
        invoiceFileIOUtil.write(invoice);
        invoiceFileIOUtil.write(invoice1);
        invoiceFileIOUtil.write(invoice2);
//        System.out.println(invoiceFileIOUtil.read("Invoice1"));
    }
}