package com.example.CarDealership.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CarDealership.model.Customer;
import com.example.CarDealership.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public String saveCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            return "Email already exists.";
        }
        customerRepository.save(customer);
        return "Customer saved successfully.";
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getCustomersByLocationName(String name) {
        return customerRepository.findByLocation_Name(name);
    }

    public List<Customer> getCustomersByProvince(String provinceName) {
        return customerRepository.findByProvinceName(provinceName);
    }

    public String updateCustomer(String id, Customer updatedCustomer) {
        Optional<Customer> existing = customerRepository.findById(id);
        if (existing.isPresent()) {
            Customer customer = existing.get();
            customer.setFirstName(updatedCustomer.getFirstName());
            customer.setLastName(updatedCustomer.getLastName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            customer.setLocation(updatedCustomer.getLocation());
            customerRepository.save(customer);
            return "Customer updated successfully.";
        }
        return "Customer not found.";
    }

    public String deleteCustomer(String id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return "Customer deleted successfully.";
        }
        return "Customer not found.";
    }
}