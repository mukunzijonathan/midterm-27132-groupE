package com.example.CarDealership.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.CarDealership.model.Car;
import com.example.CarDealership.repository.CarRepository;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    // Save a new car
    public String saveCar(Car car) {
        boolean exists = carRepository.existsByBrandAndModelAndYear(
            car.getBrand(),
            car.getModel(),
            car.getYear()
        );
        if (exists) {
            return "Car already exists.";
        }
        carRepository.save(car);
        return "Car saved successfully.";
    }

    // Get all cars — no pagination
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // Get all cars — WITH pagination
    // pageNumber starts at 0, pageSize is how many per page
    public Page<Car> getAllCarsPaginated(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return carRepository.findAll(pageable);
    }

    // Get all cars — sorted by price ascending
    public List<Car> getAllCarsSortedByPrice() {
        return carRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
    }

    // Get all cars — sorted by year descending
    public List<Car> getAllCarsSortedByYear() {
        return carRepository.findAll(Sort.by(Sort.Direction.DESC, "year"));
    }

    // Get by ID
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    // Get by brand
    public List<Car> getCarsByBrand(String brand) {
        return carRepository.findByBrand(brand);
    }

    // Update
    public String updateCar(Long id, Car updatedCar) {
        Optional<Car> existing = carRepository.findById(id);
        if (existing.isPresent()) {
            Car car = existing.get();
            car.setBrand(updatedCar.getBrand());
            car.setModel(updatedCar.getModel());
            car.setYear(updatedCar.getYear());
            car.setPrice(updatedCar.getPrice());
            carRepository.save(car);
            return "Car updated successfully.";
        }
        return "Car not found.";
    }

    // Delete
    public String deleteCar(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return "Car deleted successfully.";
        }
        return "Car not found.";
    }
}