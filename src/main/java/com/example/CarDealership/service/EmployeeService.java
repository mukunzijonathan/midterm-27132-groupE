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

    // Save a new employee
    public String saveEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            return "Email already exists.";
        }
        employeeRepository.save(employee);
        return "Employee saved successfully.";
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get by ID
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    // Get by province name
    public List<Employee> getEmployeesByProvince(String province) {
        return employeeRepository.findByLocation_Province(province);
    }

    // Get by province code
    public List<Employee> getEmployeesByProvinceCode(String provinceCode) {
        return employeeRepository.findByLocation_Province(provinceCode);
    }

    // Update
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

    // Delete
    public String deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return "Employee deleted successfully.";
        }
        return "Employee not found.";
    }
}