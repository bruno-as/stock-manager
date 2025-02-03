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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Component("BUY")
public class BuyTransactionProcessor implements TransactionProcessor {

    private final TransactionRepository transactionRepository;
    private final AssetRepository assetRepository;
    private final PortfolioRepository portfolioRepository;

    public BuyTransactionProcessor(TransactionRepository transactionRepository, AssetRepository assetRepository, PortfolioRepository portfolioRepository) {
        this.transactionRepository = transactionRepository;
        this.assetRepository = assetRepository;
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public Transaction processTransaction(TransactionDTO transactionDTO, User user) {
        Asset asset = assetRepository.findById(transactionDTO.getAssetId())
                .orElseThrow(() -> new IllegalArgumentException("Asset not found"));

        BigDecimal total = transactionDTO.getQuantity().multiply(transactionDTO.getPrice());

        Portfolio portfolio = portfolioRepository.findByUserAndAsset(user, asset)
                .orElseGet(() -> Portfolio.builder().user(user).asset(asset).build());

        BigDecimal newQuantity = portfolio.getQuantity().add(transactionDTO.getQuantity());
        BigDecimal totalInvested = portfolio.getTotalInvested().add(total);
        BigDecimal newAveragePrice = totalInvested.divide(newQuantity, 2, RoundingMode.HALF_UP);

        portfolio.setQuantity(newQuantity);
        portfolio.setAveragePrice(newAveragePrice);
        portfolio.setTotalInvested(totalInvested);
        portfolio.setUpdatedAt(LocalDateTime.now());

        portfolioRepository.save(portfolio);

        return saveTransaction(transactionDTO, user, asset, total);
    }

    private Transaction saveTransaction(TransactionDTO transactionDTO, User user, Asset asset, BigDecimal total) {
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAsset(asset);
        transaction.setType("BUY");
        transaction.setQuantity(transactionDTO.getQuantity());
        transaction.setPrice(transactionDTO.getPrice());
        transaction.setTotal(total);
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
}