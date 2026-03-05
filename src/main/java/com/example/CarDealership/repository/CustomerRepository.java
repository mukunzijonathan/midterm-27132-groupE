package com.example.CarDealership.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.CarDealership.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    boolean existsByEmail(String email);

    Customer findByEmail(String email);

    // Find by location name (any level)
    List<Customer> findByLocation_Name(String name);

    // Find customers by province — traverses tree up to 4 levels
    @Query("SELECT c FROM Customer c WHERE " +
           "c.location.name = :name OR " +
           "c.location.parent.name = :name OR " +
           "c.location.parent.parent.name = :name OR " +
           "c.location.parent.parent.parent.name = :name OR " +
           "c.location.parent.parent.parent.parent.name = :name")
    List<Customer> findByProvinceName(@Param("name") String name);
}