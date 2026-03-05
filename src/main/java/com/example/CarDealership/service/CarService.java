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

    public String saveCar(Car car) {
        if (carRepository.existsByBrandAndModelAndYear(
                car.getBrand(), car.getModel(), car.getYear())) {
            return "Car already exists.";
        }
        carRepository.save(car);
        return "Car saved successfully.";
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Page<Car> getAllCarsPaginated(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return carRepository.findAll(pageable);
    }

    public List<Car> getAllCarsSortedByPrice() {
        return carRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
    }

    public List<Car> getAllCarsSortedByYear() {
        return carRepository.findAll(Sort.by(Sort.Direction.DESC, "year"));
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public List<Car> getCarsByBrand(String brand) {
        return carRepository.findByBrand(brand);
    }

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

    public String deleteCar(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return "Car deleted successfully.";
        }
        return "Car not found.";
    }
}