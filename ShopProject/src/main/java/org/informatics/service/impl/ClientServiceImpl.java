package org.informatics.service.impl;

import org.informatics.data.*;
import org.informatics.service.ClientService;
import org.informatics.service.InvoiceService;
import org.informatics.service.ItemService;

import java.util.Date;

public class ClientServiceImpl implements ClientService {
    private final InvoiceService invoiceService;
    private final ItemService itemService;

    public ClientServiceImpl(InvoiceService invoiceService, ItemService itemService) {
        this.invoiceService = invoiceService;
        this.itemService = itemService;
    }

    @Override
    public void addToCart(ClientEntity clientEntity, ItemEntity itemEntity, Integer quantity) {
        clientEntity.getCartEntity().getItems().put(itemEntity, quantity);
    }

    @Override
    public void checkout(CartEntity cartEntity, CashRegisterEntity cashRegisterEntity) {
        ShopEntity shopEntity = cashRegisterEntity.getShopEntity();

        validateItemAvailability(cartEntity, shopEntity);

        InvoiceEntity invoiceEntity = invoiceService.createInvoice(shopEntity);
        invoiceEntity.setDate(new Date());
        invoiceEntity.setEmployeeEntity(cashRegisterEntity.getEmployeeEntity());
        invoiceEntity.setInvoiceItemData(cartEntity.getItems());
        invoiceEntity.setTotalPrice(calculateTotalPrice(cartEntity, shopEntity));

        // Remove items from Shop
    }

    private void validateItemAvailability(CartEntity cartEntity, ShopEntity shopEntity) {
        for (ItemEntity itemEntity : cartEntity.getItems().keySet()) {
            if (shopEntity.getAvailableItems().get(itemEntity) < cartEntity.getItems().get(itemEntity)) {
                throw new RuntimeException();
            }
        }
    }

    private Double calculateTotalPrice(CartEntity cartEntity, ShopEntity shopEntity) {
        double total = 0;
        for(ItemEntity itemEntity : cartEntity.getItems().keySet()) {
            int quantity = cartEntity.getItems().get(itemEntity);
            double price = itemEntity.getPrice();

            total += quantity * ((price + calculateMarkup(price, shopEntity)) -
                    calculateDiscount(price, itemEntity.getExpiryDate()));
        }
        return total;
    }

    private Double calculateMarkup(Double price, ShopEntity shopEntity) {
        double markup = shopEntity.getMarkup();
        return price * markup/100;
    }

    private Double calculateDiscount(Double price, Date expiryDate) {
        double discount = itemService.getDiscount(expiryDate);
        return price * discount/100;
    }
}
