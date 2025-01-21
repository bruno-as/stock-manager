package com.bruno.stockmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinazonResponse {

    @JsonProperty("data")
    private List<FinazonAssetDataResponse> finazonAssetDataResponses;

}