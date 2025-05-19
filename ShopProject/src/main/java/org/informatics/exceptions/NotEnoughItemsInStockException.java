package org.informatics.exceptions;

public class NotEnoughItemsInStockException extends RuntimeException {
    public NotEnoughItemsInStockException(String message) {
        super(message);
    }
}
