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
        // Tests
        // Populate Main class/method -> use changeCashRegister + Serialization/Deserialization
        InvoiceService invoiceService = new InvoiceServiceImpl(new InvoiceSequenceServiceImpl());

        Employee employee = new Employee("John Doe", 1L, 2000D);
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Shop shop = new Shop(1, employees, 200D);
        Shop shop2 = new Shop(2, null, 100D);
        Date date = new Date();
        Double totalPrice = 420.69D;
        Map<String, List<Item>> items = new HashMap<>();



        List<Item> itemsForDelivery = new ArrayList<>();
        itemsForDelivery.add(new Item(1L, "Kashkaval", 13.70D, Category.FOOD, new Date()));
        itemsForDelivery.add(new Item(2L, "Halva", 6.20D, Category.FOOD, new GregorianCalendar(2026, Calendar.MARCH, 27).getTime()));
        itemsForDelivery.add(new Item(3L, "Chorapi", 5.70D, Category.NON_FOOD, null));
        itemsForDelivery.add(new Item(4L, "Meso", 24.35D, Category.FOOD, new GregorianCalendar(2025, Calendar.MAY, 20).getTime()));
        itemsForDelivery.add(new Item(5L, "Voda", 1.50D,  Category.FOOD, new GregorianCalendar(2028, Calendar.JANUARY, 1).getTime()));

        DeliveryService deliveryService = new DeliveryServiceImpl();
        deliveryService.delivery(itemsForDelivery, shop);

        List<Item> kashkavals = new ArrayList<>();
        kashkavals.add(new Item(1L, "Kashkaval", 13.70D, Category.FOOD, new Date()));
        kashkavals.add(new Item(1L, "Kashkaval", 13.70D, Category.FOOD, new Date()));

        items.put("Kashkaval", kashkavals);
        items.put("Halva", Collections.singletonList(new Item(2L, "Halva", 6.20D, Category.FOOD, new GregorianCalendar(2026, Calendar.MARCH, 27).getTime())));
        items.put("Chorapi", Collections.singletonList(new Item(3L, "Chorapi", 5.70D, Category.NON_FOOD, null)));
        items.put("Meso", Collections.singletonList(new Item(4L, "Meso", 24.35D, Category.FOOD, new GregorianCalendar(2025, Calendar.MAY, 20).getTime())));
        items.put("Voda", Collections.singletonList(new Item(5L, "Voda", 1.50D,  Category.FOOD, new GregorianCalendar(2028, Calendar.JANUARY, 1).getTime())));

        Cart cart = new Cart();

        Client client = new Client();
        client.setCart(cart);
        client.setBudget(1000D);

        ItemService itemService = new ItemServiceImpl();

        ShopService shopService = new ShopServiceImpl(itemService);



        InvoiceFileIOUtil invoiceFileIOUtil = new InvoiceFileIOUtil(shopService);

        ClientService clientService = new ClientServiceImpl(invoiceService, invoiceFileIOUtil, shopService);

        clientService.addToCart(client, "Kashkaval", 2, shop);
        clientService.addToCart(client, "Halva", 1, shop);
        clientService.addToCart(client, "Chorapi", 1, shop);
        clientService.addToCart(client, "Meso", 1, shop);
        clientService.addToCart(client, "Voda", 1, shop);


        CashRegister cashRegister = new CashRegister(shop);
        cashRegister.setEmployee(employee);

        clientService.payAtCashRegister(cart, cashRegister, client);

        try {
            System.out.println(invoiceFileIOUtil.read("Invoice1", 1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        shop.addToAvailableItems(items);

        ItemExpirationScheduler itemExpirationScheduler = new ItemExpirationScheduler(shop, shopService, Executors.newScheduledThreadPool(1));

        shopService.countInvoices(shop.getId());
        shopService.calculateTotalIncome(shop.getId());
        shopService.calculateExpenses(shop);

        itemExpirationScheduler.shutdown();
    }
}