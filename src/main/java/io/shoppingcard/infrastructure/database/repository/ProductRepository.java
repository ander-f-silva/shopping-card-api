package io.shoppingcard.infrastructure.database.repository;

import io.shoppingcard.infrastructure.database.entity.ProductEntity;

import java.util.List;

public interface ProductRepository {
    ProductEntity findByIdAndIsGift(Long id, Boolean isGift);

    List<ProductEntity> findByIsGif(Boolean isGift);
}
