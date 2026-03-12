package com.example.CarDealership.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.CarDealership.model.Customer;
import com.example.CarDealership.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // POST — Save a customer
    // Usage: POST /api/customers/add
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        String response = customerService.saveCustomer(customer);
        if (response.equals("Customer saved successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // GET — All customers
    // Usage: GET /api/customers/all
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            return new ResponseEntity<>("No customers found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // GET — By ID
    // Usage: GET /api/customers/getById?id=uuid
    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCustomerById(@RequestParam String id) {
        return customerService.getCustomerById(id)
            .map(customer -> new ResponseEntity<Object>(customer, HttpStatus.OK))
            .orElse(new ResponseEntity<>("Customer not found.", HttpStatus.NOT_FOUND));
    }

    // GET — By location name (any level)
    // Usage: GET /api/customers/location?name=Kimironko
    @GetMapping(value = "/location", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByLocationName(@RequestParam String name) {
        List<Customer> customers = customerService.getCustomersByLocationName(name);
        if (customers.isEmpty()) {
            return new ResponseEntity<>("No customers found for location: " + name, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // GET — By province name (traverses full tree)
    // Usage: GET /api/customers/province?name=Kigali
    @GetMapping(value = "/province", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByProvince(@RequestParam String name) {
        List<Customer> customers = customerService.getCustomersByProvince(name);
        if (customers.isEmpty()) {
            return new ResponseEntity<>("No customers found in province: " + name, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // PUT — Update
    // Usage: PUT /api/customers/update?id=uuid
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCustomer(@RequestParam String id, @RequestBody Customer customer) {
        String response = customerService.updateCustomer(id, customer);
        if (response.equals("Customer updated successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // DELETE
    // Usage: DELETE /api/customers/delete?id=uuid
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteCustomer(@RequestParam String id) {
        String response = customerService.deleteCustomer(id);
        if (response.equals("Customer deleted successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // GET — By province UUID
// Usage: GET /api/customers/provinceId?id=uuid
@GetMapping(value = "/provinceId", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<?> getByProvinceId(@RequestParam String id) {
    List<Customer> customers = customerService.getCustomersByProvinceId(id);
    if (customers.isEmpty()) {
        return new ResponseEntity<>("No customers found for province ID: " + id, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(customers, HttpStatus.OK);
}
}