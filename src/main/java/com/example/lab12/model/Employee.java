package com.example.lab12.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedQuery;

import java.math.BigDecimal;

@Entity
@Table(name = "employees")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(
        name = "Employee.findTopEarners",
        query = "SELECT e FROM Employee e WHERE e.salary > :threshold ORDER BY e.salary DESC"
)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String department;

    @Positive
    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    // JPA requires a no-arg constructor
    public Employee() {}

    public Employee(String name, String department, BigDecimal salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }
}