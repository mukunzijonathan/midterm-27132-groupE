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
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        String response = employeeService.saveEmployee(employee);
        if (response.equals("Employee saved successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // GET — All employees
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()) {
            return new ResponseEntity<>("No employees found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // GET — By ID
    // Usage: GET /api/employees/getById?id=5
    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEmployeeById(@RequestParam Long id) {
        return employeeService.getEmployeeById(id)
            .map(employee -> new ResponseEntity<Object>(employee, HttpStatus.OK))
            .orElse(new ResponseEntity<>("Employee not found.", HttpStatus.NOT_FOUND));
    }

    // GET — By province name
    // Usage: GET /api/employees/province?province=Kigali
    @GetMapping(value = "/province", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByProvince(@RequestParam String province) {
        List<Employee> employees = employeeService.getEmployeesByProvince(province);
        if (employees.isEmpty()) {
            return new ResponseEntity<>("No employees found in province: " + province, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // GET — By province code
    // Usage: GET /api/employees/provinceCode?provinceCode=KGL
    @GetMapping(value = "/provinceCode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByProvinceCode(@RequestParam String provinceCode) {
        List<Employee> employees = employeeService.getEmployeesByProvinceCode(provinceCode);
        if (employees.isEmpty()) {
            return new ResponseEntity<>("No employees found for code: " + provinceCode, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // PUT — Update
    // Usage: PUT /api/employees/update?id=5
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEmployee(@RequestParam Long id, @RequestBody Employee employee) {
        String response = employeeService.updateEmployee(id, employee);
        if (response.equals("Employee updated successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // DELETE
    // Usage: DELETE /api/employees/delete?id=5
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteEmployee(@RequestParam Long id) {
        String response = employeeService.deleteEmployee(id);
        if (response.equals("Employee deleted successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}