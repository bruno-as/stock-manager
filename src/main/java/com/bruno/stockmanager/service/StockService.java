package com.bruno.stockmanager.service;

import com.bruno.stockmanager.dto.StockDTO;
import com.bruno.stockmanager.entity.Stock;
import com.bruno.stockmanager.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock addStock(StockDTO stockDTO) {
        Stock stock = Stock.builder().build();
        stock.setTicker(stockDTO.getTicker());
        stock.setName(stockDTO.getName());
        stock.setQuantity(stockDTO.getQuantity());
        stock.setAveragePrice(stockDTO.getAveragePrice());
        stock.setCurrency(stockDTO.getCurrency());
        stock.setMarket(stockDTO.getMarket());
        stock.setPurchaseDate(stockDTO.getPurchaseDate());

        return stockRepository.save(stock);
    }
}