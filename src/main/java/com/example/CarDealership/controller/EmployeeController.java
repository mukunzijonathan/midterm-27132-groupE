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

import com.example.CarDealership.model.Employee;
import com.example.CarDealership.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // POST — Add an employee
    // Usage: POST /api/employees/add
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        String response = employeeService.saveEmployee(employee);
        if (response.equals("Employee saved successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // GET — All employees
    // Usage: GET /api/employees/all
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            return new ResponseEntity<>("No employees found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // GET — By ID
    // Usage: GET /api/employees/getById?id=1
    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEmployeeById(@RequestParam Long id) {
        return employeeService.getEmployeeById(id)
            .map(employee -> new ResponseEntity<Object>(employee, HttpStatus.OK))
            .orElse(new ResponseEntity<>("Employee not found.", HttpStatus.NOT_FOUND));
    }

    // GET — By location name
    // Usage: GET /api/employees/location?name=Gasabo
    @GetMapping(value = "/location", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByLocationName(@RequestParam String name) {
        List<Employee> employees = employeeService.getEmployeesByLocationName(name);
        if (employees.isEmpty()) {
            return new ResponseEntity<>("No employees found for location: " + name, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // GET — By province name (traverses full tree)
    // Usage: GET /api/employees/province?name=Kigali
    @GetMapping(value = "/province", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByProvince(@RequestParam String name) {
        List<Employee> employees = employeeService.getEmployeesByProvince(name);
        if (employees.isEmpty()) {
            return new ResponseEntity<>("No employees found in province: " + name, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // PUT — Update
    // Usage: PUT /api/employees/update?id=1
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEmployee(@RequestParam Long id, @RequestBody Employee employee) {
        String response = employeeService.updateEmployee(id, employee);
        if (response.equals("Employee updated successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // DELETE
    // Usage: DELETE /api/employees/delete?id=1
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteEmployee(@RequestParam Long id) {
        String response = employeeService.deleteEmployee(id);
        if (response.equals("Employee deleted successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}