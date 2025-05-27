package org.informatics;

import org.informatics.data.*;
import org.informatics.scheduler.ItemExpirationScheduler;
import org.informatics.service.*;
import org.informatics.service.impl.*;
import org.informatics.utils.fileio.InvoiceFileIOUtil;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        // Services
        InvoiceService invoiceService = new InvoiceServiceImpl(new InvoiceSequenceServiceImpl());
        ItemService itemService = new ItemServiceImpl();
        ShopService shopService = new ShopServiceImpl(itemService);
        InvoiceFileIOUtil invoiceFileIOUtil = new InvoiceFileIOUtil(shopService);
        ClientService clientService = new ClientServiceImpl(invoiceService, invoiceFileIOUtil, shopService);
        DeliveryService deliveryService = new DeliveryServiceImpl();
        InvoiceSerializationDeserializationService invoiceSerializationService = new InvoiceSerializationDeserializationServiceImpl();

        // Employees and Shop
        Employee employee1 = new Employee("John Doe", 1L, 2000D);
        Employee employee2 = new Employee("Jane Smith", 2L, 2200D);
        Shop shop = new Shop(1, new ArrayList<>(List.of(employee1, employee2)), 200D);

        // CashRegister
        CashRegister cashRegister = new CashRegister(shop);
        cashRegister.setEmployee(employee1);

        List<Item> itemsForDelivery = List.of(
                new Item(1L, "Kashkaval", 13.70D, Category.FOOD, new Date()), // expired
                new Item(2L, "Halva", 6.20D, Category.FOOD, new GregorianCalendar(2026, Calendar.MARCH, 27).getTime()),
                new Item(3L, "Halva", 6.20D, Category.FOOD, new GregorianCalendar(2026, Calendar.MARCH, 27).getTime()),
                new Item(4L, "Halva", 6.20D, Category.FOOD, new GregorianCalendar(2026, Calendar.MARCH, 27).getTime()),

                new Item(5L, "Chorapi", 5.70D, Category.NON_FOOD, null),
                new Item(6L, "Chorapi", 5.70D, Category.NON_FOOD, null),

                new Item(7L, "Meso", 24.35D, Category.FOOD, new GregorianCalendar(2025, Calendar.MAY, 20).getTime()), // expired

                new Item(8L, "Voda", 1.50D, Category.FOOD, new GregorianCalendar(2028, Calendar.JANUARY, 1).getTime()),
                new Item(9L, "Voda", 1.50D, Category.FOOD, new GregorianCalendar(2028, Calendar.JANUARY, 1).getTime()),
                new Item(10L, "Voda", 1.50D, Category.FOOD, new GregorianCalendar(2028, Calendar.JANUARY, 1).getTime()),

                new Item(11L, "Cheese", 7.90D, Category.FOOD, new GregorianCalendar(2027, Calendar.JUNE, 15).getTime()),
                new Item(12L, "Cheese", 7.90D, Category.FOOD, new GregorianCalendar(2027, Calendar.JUNE, 15).getTime()),

                new Item(13L, "Toothpaste", 3.50D, Category.NON_FOOD, null),
                new Item(14L, "Toothpaste", 3.50D, Category.NON_FOOD, null),

                new Item(15L, "Shampoo", 9.80D, Category.NON_FOOD, null),
                new Item(16L, "Shampoo", 9.80D, Category.NON_FOOD, null),

                new Item(17L, "Juice", 2.20D, Category.FOOD, new GregorianCalendar(2026, Calendar.OCTOBER, 5).getTime()),
                new Item(18L, "Juice", 2.20D, Category.FOOD, new GregorianCalendar(2026, Calendar.OCTOBER, 5).getTime()),
                new Item(19L, "Juice", 2.20D, Category.FOOD, new GregorianCalendar(2026, Calendar.OCTOBER, 5).getTime()),
                new Item(20L, "Juice", 2.20D, Category.FOOD, new GregorianCalendar(2026, Calendar.OCTOBER, 5).getTime())
        );
        deliveryService.delivery(itemsForDelivery, shop);

        // Scheduler
        ItemExpirationScheduler scheduler = new ItemExpirationScheduler(shop, shopService, Executors.newScheduledThreadPool(1));
        System.out.println("\n[Waiting for scheduler to check expired items...]\n");
        try {
            // Wait for expired items to be removed
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // CLIENT 1 - John Doe handles
        Client client1 = new Client();
        client1.setBudget(1000D);
        client1.setCart(new Cart());

        clientService.addToCart(client1, "Halva", 1, shop);
        clientService.addToCart(client1, "Chorapi", 2, shop);
        clientService.addToCart(client1, "Voda", 3, shop);
        // expired, should be rejected
        clientService.addToCart(client1, "Meso", 1, shop);
        clientService.payAtCashRegister(client1.getCart(), cashRegister, client1);

        // Read first invoice
        try {
            String invoiceText1 = invoiceFileIOUtil.read("Invoice1", shop.getId());
            System.out.println("\nInvoice 1 read from file:");
            System.out.println(invoiceText1);
        } catch (IOException e) {
            System.err.println("Failed to read Invoice1: " + e.getMessage());
        }

        // Switch to Jane Smith
        cashRegister.setEmployee(employee2);
        System.out.println("\nCash register switched to: " + employee2.name());

        // CLIENT 2 - Jane handles
        Client client2 = new Client();
        client2.setBudget(500D);
        client2.setCart(new Cart());

        clientService.addToCart(client2, "Cheese", 2, shop);
        clientService.addToCart(client2, "Toothpaste", 1, shop);
        clientService.addToCart(client2, "Juice", 4, shop);
        clientService.addToCart(client2, "Shampoo", 1, shop);
        clientService.payAtCashRegister(client2.getCart(), cashRegister, client2);

        // Read second invoice
        try {
            String invoiceText2 = invoiceFileIOUtil.read("Invoice2", shop.getId());
            System.out.println("\nInvoice 2 read from file:");
            System.out.println(invoiceText2);
        } catch (IOException e) {
            System.err.println("Failed to read Invoice2: " + e.getMessage());
        }

        // Serialize
        Invoice invoiceToSerialize = invoiceService.createInvoice(
                shop, employee2, new Date(), 123.45, client2.getCart().getItems()
        );
        try {
            invoiceSerializationService.serializeInvoice("serialized_invoice.ser", invoiceToSerialize);
            System.out.println("Invoice serialized successfully.");
        } catch (Exception e) {
            System.out.println("Serialization failed:");
            e.printStackTrace();
        }

        // Deserialize
        try {
            Invoice deserializedInvoice = invoiceSerializationService.deserializeInvoice("serialized_invoice.ser");
            if (deserializedInvoice != null) {
                System.out.println("\nDeserialized Invoice:");
                System.out.println(deserializedInvoice);
            }
        } catch (Exception e) {
            System.out.println("Deserialization failed:");
            e.printStackTrace();
        }

        System.out.println();

        // Stats
        shopService.countInvoices(shop.getId());
        shopService.calculateTotalIncome(shop.getId());
        shopService.calculateExpenses(shop);

        // Shutdown scheduler
        scheduler.shutdown();
    }
}
