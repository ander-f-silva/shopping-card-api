package io.shoppingcard.infrastructure.database.repository;

import io.shoppingcard.infrastructure.database.entity.ProductEntity;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;

@Singleton
@AllArgsConstructor
class ProductMemoryRepository implements ProductRepository {
    @Inject
    private List<ProductEntity> products;

    @Override
    public ProductEntity findById(Long id) {
       return products.stream()
               .filter( productEntity -> productEntity.getId().equals(id))
               .findFirst()
               .orElseThrow(() -> new NoSuchElementException("The product not exist"));
    }
}
