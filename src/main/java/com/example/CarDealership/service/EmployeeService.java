package com.example.CarDealership.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CarDealership.model.Employee;
import com.example.CarDealership.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public String saveEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            return "Email already exists.";
        }
        employeeRepository.save(employee);
        return "Employee saved successfully.";
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getEmployeesByLocationName(String name) {
        return employeeRepository.findByLocation_Name(name);
    }

    public List<Employee> getEmployeesByProvince(String provinceName) {
        return employeeRepository.findByProvinceName(provinceName);
    }

    public String updateEmployee(Long id, Employee updatedEmployee) {
        Optional<Employee> existing = employeeRepository.findById(id);
        if (existing.isPresent()) {
            Employee employee = existing.get();
            employee.setFirstName(updatedEmployee.getFirstName());
            employee.setLastName(updatedEmployee.getLastName());
            employee.setEmail(updatedEmployee.getEmail());
            employee.setLocation(updatedEmployee.getLocation());
            employeeRepository.save(employee);
            return "Employee updated successfully.";
        }
        return "Employee not found.";
    }

    public String deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return "Employee deleted successfully.";
        }
        return "Employee not found.";
    }
}