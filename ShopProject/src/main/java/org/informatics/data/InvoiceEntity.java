package org.informatics.data;

import java.util.Date;
import java.util.Map;

public class InvoiceEntity {
    private Long id;
    private EmployeeEntity employeeEntity;
    private Date date;
    private Double totalPrice;
    private Map<ItemEntity, Integer> invoiceItemData;

    public InvoiceEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<ItemEntity, Integer> getInvoiceItemData() {
        return invoiceItemData;
    }

    public void setInvoiceItemData(Map<ItemEntity, Integer> invoiceItemData) {
        this.invoiceItemData = invoiceItemData;
    }
}
