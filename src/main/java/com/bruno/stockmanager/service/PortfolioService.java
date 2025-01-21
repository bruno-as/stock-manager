package com.bruno.stockmanager.service;


import com.bruno.stockmanager.client.FinazonClient;
import com.bruno.stockmanager.dto.FinazonResponse;
import com.bruno.stockmanager.dto.UpdateAssetRequest;
import com.bruno.stockmanager.entity.Portfolio;
import com.bruno.stockmanager.entity.User;
import com.bruno.stockmanager.repository.AssetRepository;
import com.bruno.stockmanager.repository.PortfolioRepository;
import com.bruno.stockmanager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final FinazonClient finazonClient;

    public PortfolioService(PortfolioRepository portfolioRepository, AssetRepository assetRepository, UserRepository userRepository, FinazonClient finazonClient) {
        this.portfolioRepository = portfolioRepository;
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
        this.finazonClient = finazonClient;
    }

    @Transactional
    public String getPortfolioValue(Long userId, String currency) {
        List<Portfolio> portfolios = portfolioRepository.findAllByUserId(userId);

        BigDecimal totalValue = BigDecimal.ZERO;

        for (Portfolio portfolio : portfolios) {
            BigDecimal assetValue = portfolio.getQuantity().multiply(portfolio.getAveragePrice());

            if (!portfolio.getAsset().getCurrency().equalsIgnoreCase(currency)) {
                assetValue = convertCurrency(assetValue, portfolio.getAsset().getCurrency(), currency);
            }

            totalValue = totalValue.add(assetValue);
        }

        return String.format("Total portfolio value in %s: %.2f", currency, totalValue);
    }

    private BigDecimal convertCurrency(BigDecimal amount, String fromCurrency, String toCurrency) {
        // TODO: Implementar lógica para buscar a taxa de câmbio atual
        // Por enquanto, assume taxa de conversão 1:1 como exemplo
        return amount;
    }

    @Transactional
    public void updatePortfolioWithRealTimeData(UpdateAssetRequest updateAssetRequest) {

        User user = userRepository.findById(updateAssetRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + updateAssetRequest.getUserId() + " not found."));
        
        FinazonResponse response = finazonClient.fetchAssetData(updateAssetRequest);

        response.getFinazonAssetDataResponses().stream()
                .map(assetData -> assetRepository.findByTicker(assetData.getTicker()).orElse(null))
                .filter(Objects::nonNull)
                .forEach(asset -> {
                    Portfolio portfolio = portfolioRepository.findByUserAndAsset(user, asset)
                            .orElseGet(() -> {
                                Portfolio newPortfolio = new Portfolio();
                                newPortfolio.setUser(user);
                                newPortfolio.setAsset(asset);
                                newPortfolio.setQuantity(BigDecimal.ZERO);
                                newPortfolio.setAveragePrice(BigDecimal.ZERO);
                                newPortfolio.setCreatedAt(LocalDateTime.now());
                                return newPortfolio;
                            });

                    BigDecimal updatedValue = portfolio.getQuantity().multiply(asset.getPrice());
                    portfolio.setTotalInvested(updatedValue);
                    portfolio.setUpdatedAt(LocalDateTime.now());

                    portfolioRepository.save(portfolio);
                });
    }
}