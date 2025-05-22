package org.informatics.exceptions;

public class CashRegisterNotEmpty extends RuntimeException {
    public CashRegisterNotEmpty(String message) {
        super(message);
    }
}
