package com.bruno.stockmanager.controller;

import com.bruno.stockmanager.dto.AssetDTO;
import com.bruno.stockmanager.entity.Asset;
import com.bruno.stockmanager.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public ResponseEntity<Void> addAsset(@Valid @RequestBody AssetDTO assetDTO) {
        assetService.createAsset(assetDTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) {
        Asset asset = assetService.getAssetById(assetId);
        return ResponseEntity.ok(asset);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Asset>> getAssetsByUser(@PathVariable Long userId) {
        List<Asset> assets = assetService.getAssetsByUser(userId);
        return ResponseEntity.ok(assets);
    }

    @PutMapping("/{assetId}")
    public ResponseEntity<Asset> updateAsset(@PathVariable Long assetId, @Valid @RequestBody AssetDTO assetDTO) {
        Asset updatedAsset = assetService.updateAsset(assetId, assetDTO);
        return ResponseEntity.ok(updatedAsset);
    }
    
    @DeleteMapping("/{assetId}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long assetId) {
        assetService.deleteAsset(assetId);
        return ResponseEntity.noContent().build();
    }
}