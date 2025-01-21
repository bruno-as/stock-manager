package com.bruno.stockmanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAssetRequest {
    private Integer page;
    private Integer pageSize;
    private String ticker;
    private String cqs;
    private String cik;
    private String cusip;
    private String isin;
    private String compositeFigi;
    private String shareFigi;
    private String lei;

    @NotNull(message = "The userId is required.")
    private Long userId; // Identificador do usuário
}