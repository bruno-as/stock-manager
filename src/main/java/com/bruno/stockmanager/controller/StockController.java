package com.bruno.stockmanager.controller;

import com.bruno.stockmanager.dto.StockDTO;
import com.bruno.stockmanager.entity.Stock;
import com.bruno.stockmanager.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Stock> addStock(@Valid @RequestBody StockDTO stockDTO) {
        Stock newStock = stockService.addStock(stockDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStock);
    }
}