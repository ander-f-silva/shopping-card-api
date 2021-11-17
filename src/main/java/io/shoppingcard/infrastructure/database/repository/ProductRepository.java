package io.shoppingcard.infrastructure.database.repository;

import io.shoppingcard.infrastructure.database.entity.ProductEntity;

public interface ProductRepository {
    ProductEntity findById(Long id);
}
