package org.informatics.service;

import org.informatics.data.*;

public interface ClientService {
    void addToCart(Client client, String itemName, Integer quantity, Shop shop);
    void payAtCashRegister(Cart cart, CashRegister cashRegister, Client client);
}
