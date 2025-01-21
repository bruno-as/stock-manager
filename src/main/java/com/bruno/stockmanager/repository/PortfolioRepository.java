package com.bruno.stockmanager.repository;

import com.bruno.stockmanager.entity.Asset;
import com.bruno.stockmanager.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findAllByAsset(Asset asset);

    List<Portfolio> findAllByUserId(Long userId);
}
