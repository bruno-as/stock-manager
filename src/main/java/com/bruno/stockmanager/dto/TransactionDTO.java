package com.bruno.stockmanager.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    @NotNull(message = "The asset ID is required.")
    private Long assetId;

    @NotBlank(message = "The transaction type is required.")
    private String type; // BUY, SELL, SPLIT

    @NotNull(message = "The quantity is required.")
    @DecimalMin(value = "0.01", message = "The quantity must be greater than zero.")
    private BigDecimal quantity;

    @NotNull(message = "The price is required.")
    @DecimalMin(value = "0.01", message = "The price must be greater than zero.")
    private BigDecimal price;

    @NotNull(message = "The user ID is required.")
    private Long userId;

    private LocalDateTime transactionDate = LocalDateTime.now();
}
