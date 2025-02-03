package com.bruno.stockmanager.strategy;

import com.bruno.stockmanager.dto.TransactionDTO;
import com.bruno.stockmanager.entity.Asset;
import com.bruno.stockmanager.entity.Portfolio;
import com.bruno.stockmanager.entity.Transaction;
import com.bruno.stockmanager.entity.User;
import com.bruno.stockmanager.repository.AssetRepository;
import com.bruno.stockmanager.repository.PortfolioRepository;
import com.bruno.stockmanager.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("SELL")
public class SellTransactionProcessor implements TransactionProcessor {

    private final TransactionRepository transactionRepository;
    private final AssetRepository assetRepository;
    private final PortfolioRepository portfolioRepository;

    public SellTransactionProcessor(TransactionRepository transactionRepository, AssetRepository assetRepository, PortfolioRepository portfolioRepository) {
        this.transactionRepository = transactionRepository;
        this.assetRepository = assetRepository;
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public Transaction processTransaction(TransactionDTO transactionDTO, User user) {
        Asset asset = assetRepository.findById(transactionDTO.getAssetId())
                .orElseThrow(() -> new IllegalArgumentException("Asset not found"));

        Portfolio portfolio = portfolioRepository.findByUserAndAsset(user, asset)
                .orElseThrow(() -> new IllegalStateException("User does not own this asset"));

        if (portfolio.getQuantity().compareTo(transactionDTO.getQuantity()) < 0) {
            throw new IllegalStateException("Not enough shares to sell.");
        }

        portfolio.setQuantity(portfolio.getQuantity().subtract(transactionDTO.getQuantity()));
        portfolio.setUpdatedAt(LocalDateTime.now());

        portfolioRepository.save(portfolio);

        return saveTransaction(transactionDTO, user, asset);
    }

    private Transaction saveTransaction(TransactionDTO transactionDTO, User user, Asset asset) {
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAsset(asset);
        transaction.setType("SELL");
        transaction.setQuantity(transactionDTO.getQuantity());
        transaction.setPrice(transactionDTO.getPrice());
        transaction.setTotal(transactionDTO.getQuantity().multiply(transactionDTO.getPrice()));
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
}