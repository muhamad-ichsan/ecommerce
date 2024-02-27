package com.backend.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.ecommerce.dto.TransactionDto;
import com.backend.ecommerce.model.Transaction;
import com.backend.ecommerce.service.TransactionService;
import com.backend.ecommerce.util.ResponseData;
import com.backend.ecommerce.util.ResponseHandler;

@RestController
@RequestMapping("/api/transactions")
public class TransactionContoller {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ResponseData<Transaction>> create(@Valid @RequestBody TransactionDto transaction, Errors errors) {
        return ResponseHandler.handleRequest(() -> {
        	return transactionService.save(transaction);
        	}, errors);
    }

    @GetMapping
    public ResponseEntity<ResponseData<Iterable<TransactionDto>>> findAll() {
        return ResponseHandler.handleRequest(() -> transactionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<TransactionDto>> findById(@PathVariable("id") Long id) {
        return ResponseHandler.handleRequest(() -> transactionService.findById(id));
    }

    @PostMapping("/generate-invoice/{id}")
    public ResponseEntity<ResponseData<String>> generateInvoice(@PathVariable("id") Long id) {
        return ResponseHandler.handleRequest(() -> {
            transactionService.generateInvoice(id);
            return "Invoice generated successfully";
        });
    }
}
