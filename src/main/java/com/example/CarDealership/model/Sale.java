package com.example.CarDealership.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate saleDate;

    @Column(nullable = false)
    private double finalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    // Many Sales → One Customer
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Many Sales → One Employee
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // Many-to-Many: One sale can have many cars
    @ManyToMany
    @JoinTable(
        name = "sale_car",
        joinColumns = @JoinColumn(name = "sale_id"),
        inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private List<Car> cars;

    // ─── Payment Method Enum ─────────────────────
    public enum PaymentMethod {
        CASH, CARD
    }

    // ─── Getters & Setters ──────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getSaleDate() { return saleDate; }
    public void setSaleDate(LocalDate saleDate) { this.saleDate = saleDate; }

    public double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(double finalPrice) { this.finalPrice = finalPrice; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public List<Car> getCars() { return cars; }
    public void setCars(List<Car> cars) { this.cars = cars; }
}