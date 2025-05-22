package org.informatics.service;

import org.informatics.data.CashRegister;
import org.informatics.data.Employee;
import org.informatics.exceptions.CashRegisterNotEmpty;
import org.informatics.exceptions.EmployeeDoesNotBelongToCashRegister;

public interface EmployeeService {
    void changeCashRegister(CashRegister cashRegisterFrom, CashRegister cashRegisterAt, Employee employee) throws EmployeeDoesNotBelongToCashRegister, CashRegisterNotEmpty;
}
