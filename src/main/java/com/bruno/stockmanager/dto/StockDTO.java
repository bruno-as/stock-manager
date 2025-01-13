package com.bruno.stockmanager.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class StockDTO {

    @NotBlank(message = "The ticker is required.")
    @Size(max = 10, message = "The ticker must have a maximum of 10 characters.")
    private String ticker;

    @NotBlank(message = "The name of the asset is required.")
    @Size(max = 100, message = "The name must have a maximum of 100 characters.")
    private String name;

    @NotNull(message = "The quantity is required.")
    @Min(value = 1, message = "The quantity must be at least 1.")
    private Integer quantity;

    @NotNull(message = "The average price is required.")
    @DecimalMin(value = "0.01", message = "The average price must be greater than zero.")
    private BigDecimal averagePrice;

    @NotBlank(message = "The currency is required.")
    @Size(max = 3, message = "The currency must have a maximum of 3 characters.")
    private String currency;

    @Size(max = 50, message = "The market must have a maximum of 50 characters.")
    private String market;

    private LocalDate purchaseDate;
}