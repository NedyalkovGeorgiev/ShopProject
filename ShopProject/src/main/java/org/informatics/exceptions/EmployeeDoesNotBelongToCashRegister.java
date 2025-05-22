package org.informatics.exceptions;

public class EmployeeDoesNotBelongToCashRegister extends RuntimeException {
    public EmployeeDoesNotBelongToCashRegister(String message) {
        super(message);
    }
}
