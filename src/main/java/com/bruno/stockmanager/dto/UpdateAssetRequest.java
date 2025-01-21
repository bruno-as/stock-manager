package com.bruno.stockmanager.dto;

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
}