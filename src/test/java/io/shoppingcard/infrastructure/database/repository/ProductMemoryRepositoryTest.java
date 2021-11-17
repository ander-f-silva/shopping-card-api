package io.shoppingcard.infrastructure.database.repository;

import io.shoppingcard.infrastructure.database.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductMemoryRepositoryTest {
    private ProductRepository productRepository;

    @BeforeEach
    void setUpAll() {
        var firstProduct = ProductEntity
                .builder()
                .id(1L)
                .amount(100L)
                .build();

        var secondProduct = ProductEntity
                .builder()
                .id(2L)
                .amount(200L)
                .build();

        var mockProducts = Arrays.asList(firstProduct, secondProduct);

        productRepository = new ProductMemoryRepository(mockProducts);
    }

    @Test
    @DisplayName("should find the product with success")
    void testFindTheProductWithSuccess() {
        var answer = productRepository.findById(2L);

        var expected = ProductEntity
                .builder()
                .id(2L)
                .amount(200L)
                .build();

        assertEquals(expected, answer);
    }

    @Test
    @DisplayName("should fail find the product when the not found")
    void testFindTheProductNotFound() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            productRepository.findById(3L);
        });

        var expectedMessage = "The product not exist";

        assertEquals(expectedMessage, exception.getMessage());
    }
}