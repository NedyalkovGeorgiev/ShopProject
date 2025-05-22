package org.informatics.service.impl;

import org.informatics.data.CashRegister;
import org.informatics.data.Employee;
import org.informatics.exceptions.CashRegisterNotEmpty;
import org.informatics.exceptions.EmployeeDoesNotBelongToCashRegister;
import org.informatics.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public void changeCashRegister(CashRegister cashRegisterFrom, CashRegister cashRegisterAt, Employee employee) throws EmployeeDoesNotBelongToCashRegister, CashRegisterNotEmpty {
        if (!cashRegisterFrom.getEmployee().equals(employee)) {
            throw new EmployeeDoesNotBelongToCashRegister("The employee with Id:" + employee.id() + " does not belong to this cash register");
        }
        if (cashRegisterFrom.getEmployee() != null) {
            throw new CashRegisterNotEmpty("The cash register has already been occupied");
        }
        cashRegisterFrom.setEmployee(null);
        cashRegisterAt.setEmployee(employee);
    }
}
