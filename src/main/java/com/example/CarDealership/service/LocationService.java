package com.example.CarDealership.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CarDealership.model.Location;
import com.example.CarDealership.repository.LocationRepository;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    // Save any location level
    public String saveLocation(Location location) {
        if (locationRepository.existsByNameAndType(
                location.getName(), location.getType())) {
            return location.getType() + " already exists.";
        }
        locationRepository.save(location);
        return location.getType() + " saved successfully.";
    }

    // Get all locations
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    // Get by ID
    public Optional<Location> getLocationById(String id) {
        return locationRepository.findById(id);
    }

    // Get all provinces
    public List<Location> getAllProvinces() {
        return locationRepository.findByType("PROVINCE");
    }

    // Get all districts under a province
    public List<Location> getDistrictsByProvince(String provinceId) {
        return locationRepository.findByParent_Id(provinceId);
    }

    // Get children of any location
    public List<Location> getChildren(String parentId) {
        return locationRepository.findByParent_Id(parentId);
    }

    // Get by name
    public List<Location> getByName(String name) {
        return locationRepository.findByName(name);
    }

    // Delete
    public String deleteLocation(String id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return "Location deleted successfully.";
        }
        return "Location not found.";
    }
}