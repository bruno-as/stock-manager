package com.bruno.stockmanager.service;

import com.bruno.stockmanager.dto.TransactionDTO;
import com.bruno.stockmanager.entity.Asset;
import com.bruno.stockmanager.entity.Transaction;
import com.bruno.stockmanager.entity.User;
import com.bruno.stockmanager.repository.TransactionRepository;
import com.bruno.stockmanager.repository.UserRepository;
import com.bruno.stockmanager.strategy.TransactionProcessor;
import com.bruno.stockmanager.strategy.TransactionProcessorFactory;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionProcessorFactory transactionProcessorFactory;

    @Mock
    private TransactionProcessor buyProcessor;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private TransactionDTO transactionDTO;
    private User mockUser;
    private Transaction mockTransaction;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);

        Asset mockAsset = new Asset();
        mockAsset.setId(1L);
        mockAsset.setTicker("AAPL");
        mockAsset.setPrice(new BigDecimal("150.00"));

        transactionDTO = new TransactionDTO();
        transactionDTO.setUserId(1L);
        transactionDTO.setAssetId(1L);
        transactionDTO.setType("BUY");
        transactionDTO.setQuantity(new BigDecimal("5"));
        transactionDTO.setPrice(new BigDecimal("160.00"));
        transactionDTO.setTransactionDate(LocalDateTime.now());

        mockTransaction = new Transaction();
        mockTransaction.setId(1L);
        mockTransaction.setUser(mockUser);
        mockTransaction.setAsset(mockAsset);
        mockTransaction.setType("BUY");
        mockTransaction.setQuantity(transactionDTO.getQuantity());
        mockTransaction.setPrice(transactionDTO.getPrice());
        mockTransaction.setTotal(transactionDTO.getQuantity().multiply(transactionDTO.getPrice()));
        mockTransaction.setTransactionDate(transactionDTO.getTransactionDate());
    }

    @Test
    void shouldRecordTransactionSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(transactionProcessorFactory.getProcessor("BUY")).thenReturn(buyProcessor);
        when(buyProcessor.processTransaction(transactionDTO, mockUser)).thenReturn(mockTransaction);

        Transaction transaction = transactionService.recordTransaction(transactionDTO);

        assertEquals("BUY", transaction.getType());
        assertEquals(new BigDecimal("5"), transaction.getQuantity());
        verify(transactionProcessorFactory, times(1)).getProcessor("BUY");
        verify(buyProcessor, times(1)).processTransaction(transactionDTO, mockUser);
    }
}