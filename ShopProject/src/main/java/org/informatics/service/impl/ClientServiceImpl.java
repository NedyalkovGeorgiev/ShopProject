package org.informatics.service.impl;

import org.informatics.data.*;
import org.informatics.exceptions.ItemNotFoundException;
import org.informatics.exceptions.NotEnoughItemsInStockException;
import org.informatics.service.ClientService;
import org.informatics.service.InvoiceService;
import org.informatics.service.ItemService;
import org.informatics.utils.fileio.InvoiceFileIOUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class ClientServiceImpl implements ClientService {
    private final InvoiceService invoiceService;
    private final ItemService itemService;
    private final InvoiceFileIOUtil invoiceFileIOUtil;

    public ClientServiceImpl(InvoiceService invoiceService, ItemService itemService, InvoiceFileIOUtil invoiceFileIOUtil) {
        this.invoiceService = invoiceService;
        this.itemService = itemService;
        this.invoiceFileIOUtil = invoiceFileIOUtil;
    }

    @Override
    public void addToCart(Client client, Item item, Integer quantity) {
        client.getCart().getItems().put(item, quantity);
    }

    @Override
    public void payAtCashRegister(Cart cart, CashRegister cashRegister) {
        Shop shop = cashRegister.getShop();

        try {
            validateItemAvailability(cart, shop);
        } catch (Exception exception) {
            System.out.println(exception);
            return;
        }

        removeItemsFromShop(shop, cart);

        Invoice invoice = createInvoice(shop, cart, cashRegister.getEmployee());

        saveInvoice(invoice);
    }

    private void validateItemAvailability(Cart cart, Shop shop) throws ItemNotFoundException, NotEnoughItemsInStockException {
        Map<Item, Integer> shopItems = shop.getAvailableItems();
        Map<Item, Integer> cartItems = cart.getItems();

        for (Item item : cartItems.keySet()) {
            if (!shopItems.containsKey(item)) {
                throw new ItemNotFoundException("The item: " + item.getName() + " not found!");
            }

            validateItemQuantity(shopItems.get(item), cartItems.get(item), item);
        }
    }

    private void validateItemQuantity(int availableShopItems, int requiredCartItems, Item item) throws NotEnoughItemsInStockException {
        if (availableShopItems < requiredCartItems) {
            throw new NotEnoughItemsInStockException("Not enough items in stock! Item required: " + item.getName() +
                    " Required quantity: " + requiredCartItems + " Available items: " + availableShopItems);
        }
    }

    private void removeItemsFromShop(Shop shop, Cart cart) {
        Map<Item, Integer> shopItems = shop.getAvailableItems();
        Map<Item, Integer> cartItems = cart.getItems();

        for (Item item : cart.getItems().keySet()) {
            shopItems.put(item, shopItems.get(item) - cartItems.get(item));
        }
    }

    private Invoice createInvoice(Shop shop, Cart cart, Employee employee) {
        Date date = new Date();
        Double totalPrice = calculateTotalPrice(cart, shop);
        Map<Item, Integer> items = cart.getItems();

        return invoiceService.createInvoice(shop, employee, date, totalPrice, items);
    }

    private Double calculateTotalPrice(Cart cart, Shop shop) {
        double total = 0;
        for(Item item : cart.getItems().keySet()) {
            int quantity = cart.getItems().get(item);
            double price = item.getPrice();

            total += quantity * ((price + calculateMarkup(price, shop)) -
                    calculateDiscount(price, item.getExpiryDate()));
        }
        return total;
    }

    private Double calculateMarkup(Double price, Shop shop) {
        double markup = shop.getMarkup();
        return price * markup/100;
    }

    private Double calculateDiscount(Double price, Date expiryDate) {
        double discount = itemService.getDiscount(expiryDate);
        return price * discount/100;
    }

    private void saveInvoice(Invoice invoice) {
        try {
            invoiceFileIOUtil.write(invoice);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File could not be found!");
        } catch (IOException ioException) {
            System.out.println("File could not be saved!");
        }
    }
}
