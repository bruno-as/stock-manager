package com.bruno.stockmanager.controller;

import com.bruno.stockmanager.dto.AssetDTO;
import com.bruno.stockmanager.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public ResponseEntity<Void> addStock(@Valid @RequestBody AssetDTO assetDTO) {
        assetService.createAsset(assetDTO);
        return ResponseEntity.status(201).build();
    }
}