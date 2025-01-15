package com.bruno.stockmanager.controller;

import com.bruno.stockmanager.dto.AssetDTO;
import com.bruno.stockmanager.entity.Asset;
import com.bruno.stockmanager.service.AssetService;
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

    private final AssetService assetService;

    public StockController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public ResponseEntity<Asset> addStock(@Valid @RequestBody AssetDTO stockDTO) {
        Asset newAsset = assetService.createAsset(stockDTO, 1L);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAsset);
    }
}