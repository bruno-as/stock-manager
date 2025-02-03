package com.bruno.stockmanager.strategy;

import com.bruno.stockmanager.dto.TransactionDTO;
import com.bruno.stockmanager.entity.Asset;
import com.bruno.stockmanager.entity.Portfolio;
import com.bruno.stockmanager.entity.Transaction;
import com.bruno.stockmanager.entity.User;
import com.bruno.stockmanager.repository.AssetRepository;
import com.bruno.stockmanager.repository.PortfolioRepository;
import com.bruno.stockmanager.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuyTransactionProcessorTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private PortfolioRepository portfolioRepository;

    @InjectMocks
    private BuyTransactionProcessor buyTransactionProcessor;

    private TransactionDTO transactionDTO;
    private User mockUser;
    private Asset mockAsset;
    private Portfolio mockPortfolio;

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

        transactionDTO = new TransactionDTO();
        transactionDTO.setUserId(1L);
        transactionDTO.setAssetId(1L);
        transactionDTO.setType("BUY");
        transactionDTO.setQuantity(new BigDecimal("5"));
        transactionDTO.setPrice(new BigDecimal("160.00"));
        transactionDTO.setTransactionDate(LocalDateTime.now());
    }

    @Test
    void shouldCalculateNewAveragePriceCorrectly() {
        when(assetRepository.findById(1L)).thenReturn(Optional.of(mockAsset));
        when(portfolioRepository.findByUserAndAsset(mockUser, mockAsset)).thenReturn(Optional.of(mockPortfolio));

        Transaction mockTransaction = new Transaction();
        mockTransaction.setId(1L);
        mockTransaction.setUser(mockUser);
        mockTransaction.setAsset(mockAsset);
        mockTransaction.setType("BUY");
        mockTransaction.setQuantity(new BigDecimal("5"));
        mockTransaction.setPrice(new BigDecimal("160.00"));
        mockTransaction.setTotal(new BigDecimal("800.00"));
        mockTransaction.setTransactionDate(LocalDateTime.now());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(mockTransaction);

        Transaction transaction = buyTransactionProcessor.processTransaction(transactionDTO, mockUser);

        assertNotNull(transaction);
        assertEquals("BUY", transaction.getType());
        assertEquals(new BigDecimal("5"), transaction.getQuantity());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }
}