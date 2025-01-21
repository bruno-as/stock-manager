package com.bruno.stockmanager.repository;

import com.bruno.stockmanager.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    List<Asset> findAllByUserId(Long userId);

    Optional<Asset> findByTicker(String ticker);
}
