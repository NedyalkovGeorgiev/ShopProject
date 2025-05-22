package org.informatics.service;

import org.informatics.data.*;

public interface ClientService {
    void addToCart(Client client, Item item, Integer quantity);
    void payAtCashRegister(Cart cart, CashRegister cashRegister, Client client);
}
