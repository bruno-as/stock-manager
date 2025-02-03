package com.bruno.stockmanager.service;

import com.bruno.stockmanager.dto.TransactionDTO;
import com.bruno.stockmanager.entity.Transaction;
import com.bruno.stockmanager.entity.User;
import com.bruno.stockmanager.repository.UserRepository;
import com.bruno.stockmanager.strategy.TransactionProcessorFactory;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionProcessorFactory transactionProcessorFactory;

    public TransactionService(UserRepository userRepository, TransactionProcessorFactory transactionProcessorFactory) {
        this.userRepository = userRepository;
        this.transactionProcessorFactory = transactionProcessorFactory;
    }

    @Transactional
    public Transaction recordTransaction(TransactionDTO transactionDTO) {
        User user = userRepository.findById(transactionDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return transactionProcessorFactory.getProcessor(transactionDTO.getType())
                .processTransaction(transactionDTO, user);
    }
}