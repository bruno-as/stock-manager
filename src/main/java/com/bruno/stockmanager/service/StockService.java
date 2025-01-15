package com.bruno.stockmanager.service;

import com.bruno.stockmanager.dto.StockDTO;
import com.bruno.stockmanager.entity.Asset;
import com.bruno.stockmanager.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Asset addStock(StockDTO stockDTO) {
        Asset asset = Asset.builder().build();
        asset.setTicker(stockDTO.getTicker());
        asset.setName(stockDTO.getName());
        asset.setQuantity(stockDTO.getQuantity());
        asset.setAveragePrice(stockDTO.getAveragePrice());
        asset.setCurrency(stockDTO.getCurrency());
        asset.setMarket(stockDTO.getMarket());
        asset.setPurchaseDate(stockDTO.getPurchaseDate());

        return stockRepository.save(asset);
    }
}