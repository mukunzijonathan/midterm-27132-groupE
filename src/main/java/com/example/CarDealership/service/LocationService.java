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

    public String saveLocation(Location location) {
        if (locationRepository.existsByNameAndType(
                location.getName(), location.getType())) {
            return location.getType() + " already exists.";
        }
        locationRepository.save(location);
        return location.getType() + " saved successfully.";
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Location> getLocationById(String id) {
        return locationRepository.findById(id);
    }

    public List<Location> getAllProvinces() {
        return locationRepository.findByType("PROVINCE");
    }

    public List<Location> getChildren(String parentId) {
        return locationRepository.findByParent_Id(parentId);
    }

    public List<Location> getByName(String name) {
        return locationRepository.findByName(name);
    }

    public String updateLocation(String id, Location updatedLocation) {
        Optional<Location> existing = locationRepository.findById(id);
        if (existing.isPresent()) {
            Location location = existing.get();
            location.setName(updatedLocation.getName());
            location.setType(updatedLocation.getType());
            location.setParent(updatedLocation.getParent());
            locationRepository.save(location);
            return "Location updated successfully.";
        }
        return "Location not found.";
    }

    public String deleteLocation(String id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return "Location deleted successfully.";
        }
        return "Location not found.";
    }
}