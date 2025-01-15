package com.bruno.stockmanager.repository;

import com.bruno.stockmanager.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Asset, Long> {
}