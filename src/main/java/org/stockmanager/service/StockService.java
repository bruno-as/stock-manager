package org.stockmanager.service;

import org.springframework.stereotype.Service;
import org.stockmanager.dto.StockDTO;
import org.stockmanager.entity.Stock;
import org.stockmanager.repository.StockRepository;

@Service
public class StockService {
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
