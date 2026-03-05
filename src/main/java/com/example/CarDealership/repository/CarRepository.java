package com.example.CarDealership.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CarDealership.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    // existsBy check
    boolean existsByBrandAndModelAndYear(String brand, String model, int year);

    // Find by brand
    List<Car> findByBrand(String brand);

    // Find by year
    List<Car> findByYear(int year);

    // Find cars cheaper than a given price — with sorting
    List<Car> findByPriceLessThan(double price, Sort sort);

    // Get all cars with pagination
    Page<Car> findAll(Pageable pageable);
}