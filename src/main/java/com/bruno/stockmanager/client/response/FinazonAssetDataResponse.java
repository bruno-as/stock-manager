package com.bruno.stockmanager.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinazonAssetDataResponse {

    @JsonProperty("ticker")
    private String ticker;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("security")
    private String security;

    @JsonProperty("mic")
    private String mic;
}