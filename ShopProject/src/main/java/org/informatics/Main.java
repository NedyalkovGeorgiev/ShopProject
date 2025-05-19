package org.informatics;

import org.informatics.data.*;
import org.informatics.scheduler.ItemExpirationScheduler;
import org.informatics.service.InvoiceService;
import org.informatics.service.ShopService;
import org.informatics.service.impl.InvoiceSequenceServiceImpl;
import org.informatics.service.impl.InvoiceServiceImpl;
import org.informatics.service.impl.ShopServiceImpl;
import org.informatics.utils.fileio.InvoiceFileIOUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Tests
        // Populate Main class/method
        // Populate shop expenses/income
        // Serialization and Deserialization
        // Employees to be able to change registers
        // Use getDiscount
        // List with delivered and sold items
        // At the end of the main method add shutdown for the scheduler


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
        try {
            invoiceFileIOUtil.write(invoice);
            invoiceFileIOUtil.write(invoice1);
            invoiceFileIOUtil.write(invoice2);
        } catch (IOException ioException) {
            System.out.println("Something happened while saving files!!!");
        }
        try {
            System.out.println(invoiceFileIOUtil.read("Invoice1"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ShopService shopService = new ShopServiceImpl(shop);
        shop.setAvailableItems(items);

        new ItemExpirationScheduler(shop, shopService, 1L, Executors.newScheduledThreadPool(1));

        shopService.countInvoices();
    }
}