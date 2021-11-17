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
                .isGift(false)
                .build();

        var secondProduct = ProductEntity
                .builder()
                .id(2L)
                .amount(200L)
                .isGift(false)
                .build();

        var thirdProduct = ProductEntity
                .builder()
                .id(3L)
                .amount(0L)
                .isGift(true)
                .build();

        var mockProducts = Arrays.asList(firstProduct, secondProduct, thirdProduct);

        productRepository = new ProductMemoryRepository(mockProducts);
    }

    @Test
    @DisplayName("should find the product with success")
    void testFindTheProductWithSuccess() {
        var answer = productRepository.findByIdAndIsGift(2L, false);

        var expected = ProductEntity
                .builder()
                .id(2L)
                .amount(200L)
                .isGift(false)
                .build();

        assertEquals(expected, answer);
    }

    @Test
    @DisplayName("should fail find the product when the not found")
    void testFindTheProductNotFound() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            productRepository.findByIdAndIsGift(3L, false);
        });

        var expectedMessage = "The product not exist";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @DisplayName("should find the promotional product")
    void testFindThePromotionalProduct() {
        var answer = productRepository.findByIsGif( true);

        var productEntity = ProductEntity
                .builder()
                .id(3L)
                .amount(0L)
                .isGift(true)
                .build();

        var expected = Arrays.asList(productEntity);

        assertEquals(expected, answer);
    }
}