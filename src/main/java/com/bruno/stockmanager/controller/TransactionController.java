package com.bruno.stockmanager.controller;

import com.bruno.stockmanager.dto.TransactionDTO;
import com.bruno.stockmanager.entity.Transaction;
import com.bruno.stockmanager.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/record")
    public ResponseEntity<Transaction> recordTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        Transaction transaction = transactionService.recordTransaction(transactionDTO);
        return ResponseEntity.ok(transaction);
    }
}