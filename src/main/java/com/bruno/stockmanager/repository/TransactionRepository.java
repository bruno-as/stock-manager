package com.bruno.stockmanager.repository;

import com.bruno.stockmanager.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}