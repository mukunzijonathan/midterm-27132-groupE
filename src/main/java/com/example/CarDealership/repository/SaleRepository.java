package com.example.CarDealership.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CarDealership.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    // Find all sales by a specific customer
    List<Sale> findByCustomer_Id(String customerId);

    // Find all sales handled by a specific employee
    List<Sale> findByEmployee_Id(Long employeeId);

    // Find all sales by payment method
    List<Sale> findByPaymentMethod(Sale.PaymentMethod paymentMethod);

    // existsBy check
    boolean existsByCustomer_IdAndEmployee_Id(String customerId, Long employeeId);
}