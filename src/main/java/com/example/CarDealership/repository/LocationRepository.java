package com.example.CarDealership.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CarDealership.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {

    // Find all locations by type (PROVINCE, DISTRICT etc)
    List<Location> findByType(String type);

    // Find by name
    List<Location> findByName(String name);

    // Find by name and type
    Location findByNameAndType(String name, String type);

    // Find all children of a parent
    List<Location> findByParent_Id(String parentId);

    // existsBy check
    boolean existsByNameAndType(String name, String type);
}