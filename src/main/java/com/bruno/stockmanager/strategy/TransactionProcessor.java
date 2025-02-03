package com.bruno.stockmanager.strategy;

import com.bruno.stockmanager.dto.TransactionDTO;
import com.bruno.stockmanager.entity.Transaction;
import com.bruno.stockmanager.entity.User;

public interface TransactionProcessor {
    Transaction processTransaction(TransactionDTO transactionDTO, User user);
}
