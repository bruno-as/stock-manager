package com.bruno.stockmanager.service;

import com.bruno.stockmanager.client.FinazonClient;
import com.bruno.stockmanager.client.request.UpdateAssetRequest;
import com.bruno.stockmanager.client.response.FinazonAssetDataResponse;
import com.bruno.stockmanager.client.response.FinazonResponse;
import com.bruno.stockmanager.entity.Asset;
import com.bruno.stockmanager.entity.Portfolio;
import com.bruno.stockmanager.entity.User;
import com.bruno.stockmanager.repository.AssetRepository;
import com.bruno.stockmanager.repository.PortfolioRepository;
import com.bruno.stockmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FinazonClient finazonClient;

    @InjectMocks
    private PortfolioService portfolioService;

    private User mockUser;
    private Asset mockAsset;
    private Portfolio mockPortfolio;
    private FinazonAssetDataResponse mockFinazonAsset;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);

        mockAsset = new Asset();
        mockAsset.setId(1L);
        mockAsset.setTicker("AAPL");
        mockAsset.setPrice(new BigDecimal("150.00"));

        mockPortfolio = new Portfolio();
        mockPortfolio.setUser(mockUser);
        mockPortfolio.setAsset(mockAsset);
        mockPortfolio.setQuantity(new BigDecimal("10"));
        mockPortfolio.setAveragePrice(new BigDecimal("145.00"));
        mockPortfolio.setTotalInvested(new BigDecimal("1450.00"));

        mockFinazonAsset = new FinazonAssetDataResponse();
        mockFinazonAsset.setTicker("AAPL");
        mockFinazonAsset.setCurrency("USD");
        mockFinazonAsset.setSecurity("Apple Inc.");
        mockFinazonAsset.setMic("NASDAQ");
    }

    @Test
    void shouldCalculatePortfolioValueCorrectly() {
        when(portfolioRepository.findAllByUserId(1L)).thenReturn(List.of(mockPortfolio));

        String result = portfolioService.getPortfolioValue(1L, "USD");

        assertEquals("Total portfolio value in USD: 1450.00", result);
        verify(portfolioRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void shouldReturnZeroWhenUserHasNoAssets() {
        when(portfolioRepository.findAllByUserId(1L)).thenReturn(List.of());

        String result = portfolioService.getPortfolioValue(1L, "USD");

        assertEquals("Total portfolio value in USD: 0.00", result);
        verify(portfolioRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void shouldUpdatePortfolioWithRealTimeData() {
        UpdateAssetRequest updateAssetRequest = new UpdateAssetRequest();
        updateAssetRequest.setUserId(1L);
        updateAssetRequest.setTicker("AAPL");

        FinazonResponse mockResponse = new FinazonResponse();
        mockResponse.setFinazonAssetDataResponses(List.of(mockFinazonAsset));

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(finazonClient.fetchAssetData(updateAssetRequest)).thenReturn(mockResponse);
        when(assetRepository.findByTicker("AAPL")).thenReturn(Optional.of(mockAsset));
        when(portfolioRepository.findByUserAndAsset(mockUser, mockAsset)).thenReturn(Optional.of(mockPortfolio));

        portfolioService.updatePortfolioWithRealTimeData(updateAssetRequest);

        verify(portfolioRepository, times(1)).save(mockPortfolio);
        assertEquals(new BigDecimal("1500.00"), mockPortfolio.getTotalInvested());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        UpdateAssetRequest updateAssetRequest = new UpdateAssetRequest();
        updateAssetRequest.setUserId(999L);

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                portfolioService.updatePortfolioWithRealTimeData(updateAssetRequest));

        assertEquals("User with ID 999 not found.", exception.getMessage());
    }
}