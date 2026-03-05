package com.example.CarDealership.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CarDealership.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByCustomer_Id(String customerId);

    List<Sale> findByEmployee_Id(Long employeeId);

    List<Sale> findByPaymentMethod(Sale.PaymentMethod paymentMethod);

    boolean existsByCustomer_IdAndEmployee_Id(String customerId, Long employeeId);
}