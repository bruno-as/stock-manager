package com.bruno.stockmanager.controller;

import com.bruno.stockmanager.dto.UpdateAssetRequest;
import com.bruno.stockmanager.service.PortfolioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updatePortfolio(@Valid @RequestBody UpdateAssetRequest updateAssetRequest) {
        portfolioService.updatePortfolioWithRealTimeData(updateAssetRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/value")
    public ResponseEntity<String> getPortfolioValue(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "USD") String currency
    ) {
        String portfolioValue = portfolioService.getPortfolioValue(userId, currency);
        return ResponseEntity.ok(portfolioValue);
    }
}
