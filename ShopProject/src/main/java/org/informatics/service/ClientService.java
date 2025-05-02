package org.informatics.service;

import org.informatics.data.*;

public interface ClientService {
    void addToCart(ClientEntity clientEntity, ItemEntity itemEntity, Integer quantity);
    void checkout(CartEntity cartEntity, CashRegisterEntity cashRegisterEntity);
}
