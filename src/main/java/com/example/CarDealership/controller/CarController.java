package com.example.CarDealership.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.CarDealership.model.Car;
import com.example.CarDealership.service.CarService;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    // POST — Add a car
    // Usage: POST /api/cars/add
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCar(@RequestBody Car car) {
        String response = carService.saveCar(car);
        if (response.equals("Car saved successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // GET — All cars
    // Usage: GET /api/cars/all
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCars() {
        List<Car> cars = carService.getAllCars();
        if (cars.isEmpty()) {
            return new ResponseEntity<>("No cars found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    // GET — Paginated
    // Usage: GET /api/cars/paginated?page=0&size=5
    @GetMapping(value = "/paginated", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCarsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<Car> cars = carService.getAllCarsPaginated(page, size);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    // GET — Sorted by price ascending
    // Usage: GET /api/cars/sorted/price
    @GetMapping(value = "/sorted/price", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCarsSortedByPrice() {
        List<Car> cars = carService.getAllCarsSortedByPrice();
        if (cars.isEmpty()) {
            return new ResponseEntity<>("No cars found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    // GET — Sorted by year descending
    // Usage: GET /api/cars/sorted/year
    @GetMapping(value = "/sorted/year", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCarsSortedByYear() {
        List<Car> cars = carService.getAllCarsSortedByYear();
        if (cars.isEmpty()) {
            return new ResponseEntity<>("No cars found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    // GET — By ID
    // Usage: GET /api/cars/getById?id=5
    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCarById(@RequestParam Long id) {
        return carService.getCarById(id)
            .map(car -> new ResponseEntity<Object>(car, HttpStatus.OK))
            .orElse(new ResponseEntity<>("Car not found.", HttpStatus.NOT_FOUND));
    }

    // GET — By brand
    // Usage: GET /api/cars/brand?brand=Toyota
    @GetMapping(value = "/brand", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCarsByBrand(@RequestParam String brand) {
        List<Car> cars = carService.getCarsByBrand(brand);
        if (cars.isEmpty()) {
            return new ResponseEntity<>("No cars found for brand: " + brand, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    // PUT — Update
    // Usage: PUT /api/cars/update?id=5
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCar(@RequestParam Long id, @RequestBody Car car) {
        String response = carService.updateCar(id, car);
        if (response.equals("Car updated successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // DELETE
    // Usage: DELETE /api/cars/delete?id=5
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteCar(@RequestParam Long id) {
        String response = carService.deleteCar(id);
        if (response.equals("Car deleted successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}