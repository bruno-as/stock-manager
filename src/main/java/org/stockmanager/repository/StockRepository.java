package org.stockmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.stockmanager.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
