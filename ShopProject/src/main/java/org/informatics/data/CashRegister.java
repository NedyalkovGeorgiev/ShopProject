package org.informatics.data;

public class CashRegister {
    private Employee employee;
    private final Shop shop;

    public CashRegister(Shop shop) {
        this.shop = shop;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Shop getShop() {
        return shop;
    }
}
