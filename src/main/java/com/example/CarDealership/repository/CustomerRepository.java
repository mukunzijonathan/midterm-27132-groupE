package com.example.CarDealership.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.CarDealership.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    // existsBy — check before saving
    boolean existsByEmail(String email);

    // Find customer by email
    Customer findByEmail(String email);

    // Find all customers by location name (province, district, village etc)
    List<Customer> findByLocation_Name(String name);

    // Find all customers by location type
    List<Customer> findByLocation_Type(String type);

    // Custom query — find customers by province name navigating the tree
    @Query("SELECT c FROM Customer c WHERE c.location.name = :name " +
           "OR c.location.parent.name = :name " +
           "OR c.location.parent.parent.name = :name " +
           "OR c.location.parent.parent.parent.name = :name " +
           "OR c.location.parent.parent.parent.parent.name = :name")
    List<Customer> findByProvinceName(@Param("name") String name);
}