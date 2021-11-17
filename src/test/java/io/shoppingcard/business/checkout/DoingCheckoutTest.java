package io.shoppingcard.business.checkout;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.shoppingcard.business.discount.CalculateDiscount;
import io.shoppingcard.business.discount.GetProductDiscount;
import io.shoppingcard.business.dto.Product;
import io.shoppingcard.infrastructure.database.repository.ProductRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@MicronautTest
class DoingCheckoutTest {
    @Inject
    private DoCheckout doCheckout;

    @Inject
    private CalculateDiscount calculateDiscount;

    @Inject
    private GetProductDiscount getProductDiscount;

    @Inject
    private ProductRepository productRepository;

    @Test
    @DisplayName("should do the checkout of purchase without black Friday")
    void testDoingCheckoutWithoutBlackFriday() {
        var firstProduct = Product
                .builder()
                .id(1L)
                .quantity(2)
                .build();

        var secondProduct = Product
                .builder()
                .id(2L)
                .quantity(3)
                .build();

        var products = Arrays.asList(firstProduct, secondProduct);

        when(getProductDiscount.getPercent(anyLong())).thenReturn(0.5F);

        var answer = doCheckout.apply(products);

        verify(getProductDiscount, times(2)).getPercent(anyLong());

        var expectedFirstProduct = Product
                .builder()
                .id(1L)
                .quantity(2)
                .unitAmount(15157L)
                .totalAmount(30314L)
                .discount(30314L)
                .isGift(false)
                .build();

        var expectedSecondProduct = Product
                .builder()
                .id(2L)
                .quantity(3)
                .unitAmount(93811L)
                .totalAmount(281433L)
                .discount(187622L)
                .isGift(false)
                .build();

        var expectedProducts = Arrays.asList(expectedFirstProduct, expectedSecondProduct);

        var expectedTotalAmount = 311747;
        var expectedTotalDiscount = 217936;
        var expectedTotalAmountWithDiscount = 93811;

        assertEquals(expectedProducts, answer.getProducts());

        assertEquals(expectedTotalAmount, answer.getTotalAmount());
        assertEquals(expectedTotalDiscount, answer.getTotalDiscount());
        assertEquals(expectedTotalAmountWithDiscount, answer.getTotalAmountWithDiscount());
    }

    @MockBean(GetProductDiscount.class)
    GetProductDiscount getProductDiscount() {
        return mock(GetProductDiscount.class);
    }
}