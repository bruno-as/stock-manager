package com.bruno.stockmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AssetDTO {

    @NotBlank(message = "The ticker is required.")
    @Size(max = 10, message = "The ticker must have a maximum of 10 characters.")
    private String ticker;

    @NotBlank(message = "The market is required.")
    @Size(max = 50, message = "The market must have a maximum of 50 characters.")
    private String market;

    @NotBlank(message = "The currency is required.")
    @Size(max = 3, message = "The currency must have a maximum of 3 characters.")
    private String currency;

}
