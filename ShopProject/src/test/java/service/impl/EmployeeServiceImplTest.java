package service.impl;

import org.informatics.data.CashRegister;
import org.informatics.data.Employee;
import org.informatics.exceptions.CashRegisterNotEmpty;
import org.informatics.exceptions.EmployeeDoesNotBelongToCashRegister;
import org.informatics.service.EmployeeService;
import org.informatics.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    private EmployeeService employeeService;
    private Employee employee;
    private CashRegister fromRegister;
    private CashRegister toRegister;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeServiceImpl();
        employee = mock(Employee.class);
        fromRegister = mock(CashRegister.class);
        toRegister = mock(CashRegister.class);
    }

    @Test
    void changeCashRegister_successfullyChangesEmployee() throws Exception {
        // Arrange
        when(fromRegister.getEmployee()).thenReturn(employee);
        when(toRegister.getEmployee()).thenReturn(null);

        // Act
        employeeService.changeCashRegister(fromRegister, toRegister, employee);

        // Assert
        verify(fromRegister).setEmployee(null);
        verify(toRegister).setEmployee(employee);
    }

    @Test
    void changeCashRegister_throwsIfEmployeeDoesNotMatch() {
        // Arrange
        Employee otherEmployee = mock(Employee.class);
        when(fromRegister.getEmployee()).thenReturn(otherEmployee);

        // Act + Assert
        assertThrows(EmployeeDoesNotBelongToCashRegister.class, () ->
                employeeService.changeCashRegister(fromRegister, toRegister, employee));
    }

    @Test
    void changeCashRegister_throwsIfTargetCashRegisterIsOccupied() {
        // Arrange
        when(fromRegister.getEmployee()).thenReturn(employee);
        when(toRegister.getEmployee()).thenReturn(mock(Employee.class));

        // Act + Assert
        assertThrows(CashRegisterNotEmpty.class, () ->
                employeeService.changeCashRegister(fromRegister, toRegister, employee));
    }
}