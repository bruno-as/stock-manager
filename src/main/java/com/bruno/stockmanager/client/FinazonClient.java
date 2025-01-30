package com.bruno.stockmanager.client;

import com.bruno.stockmanager.dto.FinazonResponse;
import com.bruno.stockmanager.dto.UpdateAssetRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class FinazonClient {

    private final RestTemplate restTemplate;
    private static final String FINAZON_HOST = "api.finazon.io";
    private static final String FINAZON_LATEST_ASSET_PRICE_PATH = "/latest/finazon/us_stocks_essential/tickers";
    private static final String SCHEME = "https";

    @Value("${finazon.api.key}")
    private String apiKey;

    public FinazonClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FinazonResponse fetchAssetData(UpdateAssetRequest updateAssetRequest) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("API key is not configured. Please set FINAZON_API_KEY.");
        }

        String url = UriComponentsBuilder.newInstance()
                .scheme(SCHEME)
                .host(FINAZON_HOST)
                .path(FINAZON_LATEST_ASSET_PRICE_PATH)
                .queryParam("page", updateAssetRequest.getPage())
                .queryParam("page_size", updateAssetRequest.getPageSize())
                .queryParam("ticker", updateAssetRequest.getTicker())
                .queryParam("cqs", updateAssetRequest.getCqs())
                .queryParam("cik", updateAssetRequest.getCik())
                .queryParam("cusip", updateAssetRequest.getCusip())
                .queryParam("isin", updateAssetRequest.getIsin())
                .queryParam("composite_figi", updateAssetRequest.getCompositeFigi())
                .queryParam("share_figi", updateAssetRequest.getShareFigi())
                .queryParam("lei", updateAssetRequest.getLei())
                .queryParam("apikey", apiKey)
                .build()
                .toString();

        return restTemplate.getForObject(url, FinazonResponse.class);
    }
}
