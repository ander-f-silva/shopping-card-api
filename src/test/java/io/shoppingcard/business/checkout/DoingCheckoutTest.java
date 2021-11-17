package io.shoppingcard.business.checkout;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.shoppingcard.business.discount.CalculateDiscount;
import io.shoppingcard.business.discount.GetProductDiscount;
import io.shoppingcard.business.dto.BlackFridayEvent;
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

    @Inject
    private BlackFridayEvent blackFridayEvent;

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
        when(blackFridayEvent.isNow()).thenReturn(false);

        var answer = doCheckout.apply(products);

        verify(getProductDiscount, times(2)).getPercent(anyLong());
        verify(blackFridayEvent, times(1)).isNow();

        var expectedFirstProduct = Product
                .builder()
                .id(1L)
                .quantity(2)
                .unitAmount(15157L)
                .totalAmount(30314L)
                .discount(7578L)
                .isGift(false)
                .build();

        var expectedSecondProduct = Product
                .builder()
                .id(2L)
                .quantity(3)
                .unitAmount(93811L)
                .totalAmount(281433L)
                .discount(46905L)
                .isGift(false)
                .build();

        var expectedProducts = Arrays.asList(expectedFirstProduct, expectedSecondProduct);

        var expectedTotalAmount = 311747;
        var expectedTotalDiscount = 54483;
        var expectedTotalAmountWithDiscount = 257264;

        assertEquals(expectedProducts, answer.getProducts());

        assertEquals(expectedTotalAmount, answer.getTotalAmount());
        assertEquals(expectedTotalDiscount, answer.getTotalDiscount());
        assertEquals(expectedTotalAmountWithDiscount, answer.getTotalAmountWithDiscount());
    }

    @Test
    @DisplayName("should do the checkout of purchase with black Friday")
    void testDoingCheckoutWithBlackFriday() {
        var firstProduct = Product
                .builder()
                .id(1L)
                .quantity(1)
                .build();

        var products = Arrays.asList(firstProduct);

        when(getProductDiscount.getPercent(anyLong())).thenReturn(0.5F);
        when(blackFridayEvent.isNow()).thenReturn(true);

        var answer = doCheckout.apply(products);

        verify(getProductDiscount, times(1)).getPercent(anyLong());
        verify(blackFridayEvent, times(1)).isNow();

        var expectedFirstProduct = Product
                .builder()
                .id(1L)
                .quantity(1)
                .unitAmount(15157L)
                .totalAmount(15157L)
                .discount(7578L)
                .isGift(false)
                .build();

        var expectedSecondProduct = Product
                .builder()
                .id(6L)
                .quantity(1)
                .unitAmount(0L)
                .totalAmount(0L)
                .discount(0L)
                .isGift(true)
                .build();

        var expectedProducts = Arrays.asList(expectedFirstProduct, expectedSecondProduct);

        var expectedTotalAmount = 15157L;
        var expectedTotalDiscount = 7578L;
        var expectedTotalAmountWithDiscount = 7579L;

        assertEquals(expectedProducts, answer.getProducts());

        assertEquals(expectedTotalAmount, answer.getTotalAmount());
        assertEquals(expectedTotalDiscount, answer.getTotalDiscount());
        assertEquals(expectedTotalAmountWithDiscount, answer.getTotalAmountWithDiscount());
    }

    @MockBean(GetProductDiscount.class)
    GetProductDiscount getProductDiscount() {
        return mock(GetProductDiscount.class);
    }

    @MockBean(BlackFridayEvent.class)
    BlackFridayEvent isBlackFriday() {
        return mock(BlackFridayEvent.class);
    }
}