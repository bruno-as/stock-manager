package com.bruno.stockmanager.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

class TransactionProcessorFactoryTest {

    private TransactionProcessorFactory factory;
    private BuyTransactionProcessor buyProcessor;
    private SellTransactionProcessor sellProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        buyProcessor = mock(BuyTransactionProcessor.class);
        sellProcessor = mock(SellTransactionProcessor.class);

        factory = new TransactionProcessorFactory(Map.of(
                "BUY", buyProcessor,
                "SELL", sellProcessor
        ));
    }

    @Test
    void shouldReturnBuyProcessorForBuyType() {
        TransactionProcessor processor = factory.getProcessor("BUY");
        assertEquals(buyProcessor, processor);
    }

    @Test
    void shouldReturnSellProcessorForSellType() {
        TransactionProcessor processor = factory.getProcessor("SELL");
        assertEquals(sellProcessor, processor);
    }

    @Test
    void shouldReturnDefaultProcessorForUnknownType() {
        TransactionProcessor processor = factory.getProcessor("UNKNOWN");
        assertNull(processor);
    }
}
