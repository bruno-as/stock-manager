package com.bruno.stockmanager.strategy;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TransactionProcessorFactory {

    private final Map<String, TransactionProcessor> transactionProcessors;

    public TransactionProcessorFactory(Map<String, TransactionProcessor> transactionProcessors) {
        this.transactionProcessors = transactionProcessors;
    }

    public TransactionProcessor getProcessor(String type) {
        return transactionProcessors.getOrDefault(type.toUpperCase(),
                transactionProcessors.get("DEFAULT"));
    }
}