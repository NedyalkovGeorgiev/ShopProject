package org.informatics.service.impl;

import org.informatics.data.*;
import org.informatics.exceptions.ItemNotFoundException;
import org.informatics.exceptions.NotEnoughItemsInStockException;
import org.informatics.service.ClientService;
import org.informatics.service.InvoiceService;
import org.informatics.service.ShopService;
import org.informatics.utils.fileio.InvoiceFileIOUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ClientServiceImpl implements ClientService {
    private final InvoiceService invoiceService;
    private final InvoiceFileIOUtil invoiceFileIOUtil;
    private final ShopService shopService;

    public ClientServiceImpl(InvoiceService invoiceService, InvoiceFileIOUtil invoiceFileIOUtil, ShopService shopService) {
        this.invoiceService = invoiceService;
        this.invoiceFileIOUtil = invoiceFileIOUtil;
        this.shopService = shopService;
    }

    @Override
    public void addToCart(Client client, String itemName, Integer quantity, Shop shop) {
        Cart cart = client.getCart();

        try {
            validateItemAvailability(itemName, quantity, shop);
        } catch (ItemNotFoundException | NotEnoughItemsInStockException itemNotFoundException) {
            System.out.println(itemNotFoundException.getMessage());
            return;
        }

        Map<String, List<Item>> itemsToAddToCart = new HashMap<>();
        Map<String, List<Item>> itemsAvailableInShop = shop.getAvailableItems();

        for (int i = quantity; i > 0; i--) {
            if (itemsToAddToCart.get(itemName) == null) {
                itemsToAddToCart.put(itemName, new ArrayList<>(Collections.singletonList(itemsAvailableInShop.get(itemName).get(i - 1))));
            } else {
                itemsToAddToCart.get(itemName).add(itemsAvailableInShop.get(itemName).get(i - 1));
            }

            itemsAvailableInShop.get(itemName).remove(i - 1);
        }

        addToCart(itemsToAddToCart, cart);
    }

    private void addToCart(Map<String, List<Item>> items, Cart cart) {
        for (String itemName : items.keySet()) {
            cart.getItems().compute(itemName, (key, existingList) -> {
                if (existingList == null) {
                    return new ArrayList<>(items.get(itemName));
                } else {
                    existingList.addAll(items.get(itemName));
                    return existingList;
                }
            });
        }
    }

    @Override
    public void payAtCashRegister(Cart cart, CashRegister cashRegister, Client client) {
        Shop shop = cashRegister.getShop();
        Invoice invoice = createInvoice(shop, cart, cashRegister.getEmployee());

        updateClientBudget(client, invoice);

        shop.addToSoldItems(cart.getItems());

        saveInvoice(invoice, shop);
    }

    private void updateClientBudget(Client client, Invoice invoice) {
        client.setBudget(client.getBudget() - invoice.totalPrice());
    }

    private void validateItemAvailability(String itemName, Integer quantity, Shop shop) throws ItemNotFoundException, NotEnoughItemsInStockException {
        Map<String, List<Item>> shopItems = shop.getAvailableItems();

        if (!shopItems.containsKey(itemName)) {
            throw new ItemNotFoundException("The item: " + itemName + " not found!");
        }

        validateItemQuantity(shopItems.get(itemName), quantity, itemName);
    }

    private void validateItemQuantity(List<Item> availableShopItems, Integer quantity, String itemName) throws NotEnoughItemsInStockException {
        if (availableShopItems.size() < quantity) {
            throw new NotEnoughItemsInStockException("Not enough items in stock! Item required: " + itemName +
                    " Required quantity: " + quantity + " Available items: " + availableShopItems.size());
        }
    }

    private Invoice createInvoice(Shop shop, Cart cart, Employee employee) {
        Date date = new Date();
        Double totalPrice = calculateTotalPrice(cart, shop.getMarkup());
        Map<String, List<Item>> items = cart.getItems();

        return invoiceService.createInvoice(shop, employee, date, totalPrice, items);
    }

    private Double calculateTotalPrice(Cart cart, Double markup) {
        double total = 0;
        for(String itemName : cart.getItems().keySet()) {
            List<Item> itemsToBuy = cart.getItems().get(itemName);

            for (Item item : itemsToBuy) {
                double price = item.price();

                total += shopService.calculateMarkup(price, markup) - shopService.calculateDiscount(price, item);
            }
        }
        return total;
    }

    private void saveInvoice(Invoice invoice, Shop shop) {
        try {
            invoiceFileIOUtil.write(invoice, shop);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File could not be found!");
        } catch (IOException ioException) {
            System.out.println("File could not be saved!");
        }
    }
}
