package org.informatics.data;

public class CashRegisterEntity {
    private EmployeeEntity employeeEntity;
    private final ShopEntity shopEntity;

    public CashRegisterEntity(ShopEntity shopEntity) {
        this.shopEntity = shopEntity;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    public ShopEntity getShopEntity() {
        return shopEntity;
    }
}
