package com.example.CarDealership.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CarDealership.model.Sale;
import com.example.CarDealership.repository.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    // Save a new sale
    public String saveSale(Sale sale) {
        saleRepository.save(sale);
        return "Sale saved successfully.";
    }

    // Get all sales
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    // Get by ID
    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    // Get all sales by customer
    public List<Sale> getSalesByCustomer(String customerId) {
        return saleRepository.findByCustomer_Id(customerId);
    }

    // Get all sales by employee
    public List<Sale> getSalesByEmployee(Long employeeId) {
        return saleRepository.findByEmployee_Id(employeeId);
    }

    // Get all sales by payment method
    public List<Sale> getSalesByPaymentMethod(Sale.PaymentMethod paymentMethod) {
        return saleRepository.findByPaymentMethod(paymentMethod);
    }

    // Update
    public String updateSale(Long id, Sale updatedSale) {
        Optional<Sale> existing = saleRepository.findById(id);
        if (existing.isPresent()) {
            Sale sale = existing.get();
            sale.setSaleDate(updatedSale.getSaleDate());
            sale.setFinalPrice(updatedSale.getFinalPrice());
            sale.setPaymentMethod(updatedSale.getPaymentMethod());
            sale.setCustomer(updatedSale.getCustomer());
            sale.setEmployee(updatedSale.getEmployee());
            sale.setCars(updatedSale.getCars());
            saleRepository.save(sale);
            return "Sale updated successfully.";
        }
        return "Sale not found.";
    }

    // Delete
    public String deleteSale(Long id) {
        if (saleRepository.existsById(id)) {
            saleRepository.deleteById(id);
            return "Sale deleted successfully.";
        }
        return "Sale not found.";
    }
}