package org.stockmanager.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.stockmanager.dto.StockDTO;
import org.stockmanager.entity.Stock;
import org.stockmanager.service.StockService;

@RestController
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
