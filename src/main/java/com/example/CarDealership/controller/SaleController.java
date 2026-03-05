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

import com.example.CarDealership.model.Sale;
import com.example.CarDealership.service.SaleService;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    // POST — Create a sale
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addSale(@RequestBody Sale sale) {
        String response = saleService.saveSale(sale);
        if (response.equals("Sale saved successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // GET — All sales
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllSales() {
        List<Sale> sales = saleService.getAllSales();
        if (sales.isEmpty()) {
            return new ResponseEntity<>("No sales found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    // GET — By ID
    // Usage: GET /api/sales/getById?id=1
    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSaleById(@RequestParam Long id) {
        return saleService.getSaleById(id)
            .map(sale -> new ResponseEntity<Object>(sale, HttpStatus.OK))
            .orElse(new ResponseEntity<>("Sale not found.", HttpStatus.NOT_FOUND));
    }

    // GET — By customer
    // Usage: GET /api/sales/customer?customerId=abc-uuid
    @GetMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSalesByCustomer(@RequestParam String customerId) {
        List<Sale> sales = saleService.getSalesByCustomer(customerId);
        if (sales.isEmpty()) {
            return new ResponseEntity<>("No sales found for this customer.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    // GET — By employee
    // Usage: GET /api/sales/employee?employeeId=1
    @GetMapping(value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSalesByEmployee(@RequestParam Long employeeId) {
        List<Sale> sales = saleService.getSalesByEmployee(employeeId);
        if (sales.isEmpty()) {
            return new ResponseEntity<>("No sales found for this employee.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    // GET — By payment method
    // Usage: GET /api/sales/payment?paymentMethod=CASH
    @GetMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSalesByPaymentMethod(@RequestParam Sale.PaymentMethod paymentMethod) {
        List<Sale> sales = saleService.getSalesByPaymentMethod(paymentMethod);
        if (sales.isEmpty()) {
            return new ResponseEntity<>("No sales found for payment method: " + paymentMethod, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sales, HttpStatus.OK);
    }

    // PUT — Update
    // Usage: PUT /api/sales/update?id=1
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSale(@RequestParam Long id, @RequestBody Sale sale) {
        String response = saleService.updateSale(id, sale);
        if (response.equals("Sale updated successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // DELETE
    // Usage: DELETE /api/sales/delete?id=1
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteSale(@RequestParam Long id) {
        String response = saleService.deleteSale(id);
        if (response.equals("Sale deleted successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}