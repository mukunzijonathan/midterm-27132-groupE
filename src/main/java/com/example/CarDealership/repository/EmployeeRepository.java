package com.example.CarDealership.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.CarDealership.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);

    Employee findByEmail(String email);

    // Find by location name
    List<Employee> findByLocation_Name(String name);

    // Find employees by province — traverses tree
    @Query("SELECT e FROM Employee e WHERE " +
           "e.location.name = :name OR " +
           "e.location.parent.name = :name OR " +
           "e.location.parent.parent.name = :name OR " +
           "e.location.parent.parent.parent.name = :name OR " +
           "e.location.parent.parent.parent.parent.name = :name")
    List<Employee> findByProvinceName(@Param("name") String name);
}