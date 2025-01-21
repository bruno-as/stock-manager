package com.bruno.stockmanager.service;

import com.bruno.stockmanager.dto.AssetDTO;
import com.bruno.stockmanager.entity.Asset;
import com.bruno.stockmanager.entity.User;
import com.bruno.stockmanager.repository.AssetRepository;
import com.bruno.stockmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssetService {
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;

    public AssetService(AssetRepository assetRepository, UserRepository userRepository) {
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    public void createAsset(AssetDTO assetDTO) {
        User user = userRepository.findById(assetDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + assetDTO.getUserId() + " not found."));

        Asset asset = new Asset();
        asset.setTicker(assetDTO.getTicker());
        asset.setMarket(assetDTO.getMarket());
        asset.setCurrency(assetDTO.getCurrency());
        asset.setUser(user);
        asset.setCreatedAt(LocalDateTime.now());
        asset.setUpdatedAt(LocalDateTime.now());

        assetRepository.save(asset);
    }

    public Asset getAssetById(Long assetId) {
        return assetRepository.findById(assetId)
                .orElseThrow(() -> new IllegalArgumentException("Asset with ID " + assetId + " not found."));
    }

    public List<Asset> getAssetsByUser(Long userId) {
        return assetRepository.findAllByUserId(userId);
    }

    public Asset updateAsset(Long assetId, AssetDTO assetDTO) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new IllegalArgumentException("Asset with ID " + assetId + " not found."));

        asset.setTicker(assetDTO.getTicker());
        asset.setMarket(assetDTO.getMarket());
        asset.setCurrency(assetDTO.getCurrency());
        asset.setUpdatedAt(LocalDateTime.now());

        return assetRepository.save(asset);
    }

    public void deleteAsset(Long assetId) {
        if (!assetRepository.existsById(assetId)) {
            throw new IllegalArgumentException("Asset with ID " + assetId + " not found.");
        }
        assetRepository.deleteById(assetId);
    }
}
