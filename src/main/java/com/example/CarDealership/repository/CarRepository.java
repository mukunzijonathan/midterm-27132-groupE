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

    boolean existsByBrandAndModelAndYear(String brand, String model, int year);

    List<Car> findByBrand(String brand);

    List<Car> findByYear(int year);

    List<Car> findByPriceLessThan(double price, Sort sort);

    Page<Car> findAll(Pageable pageable);
}