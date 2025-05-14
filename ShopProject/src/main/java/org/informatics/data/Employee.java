package org.informatics.data;

public class Employee {
    private final Long id;
    private final String name;
    private Double salary;

    public Employee(String name, Long id, Double salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
