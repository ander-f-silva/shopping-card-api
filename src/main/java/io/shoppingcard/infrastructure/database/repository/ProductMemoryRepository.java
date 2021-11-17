package io.shoppingcard.infrastructure.database.repository;

import io.shoppingcard.infrastructure.database.entity.ProductEntity;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Singleton
@AllArgsConstructor
class ProductMemoryRepository implements ProductRepository {
    @Inject
    private List<ProductEntity> products;

    @Override
    public ProductEntity findByIdAndIsGift(Long id, Boolean isGift) {
        return products.stream()
                .filter(productEntity -> productEntity.getId().equals(id) && productEntity.getIsGift().equals(isGift))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("The product not exist"));
    }

    @Override
    public List<ProductEntity> findByIsGif(Boolean isGift) {
        return products.stream()
                .filter(productEntity -> productEntity.getIsGift().equals(isGift))
                .collect(Collectors.toList());
    }
}
