package io.shoppingcard.business.discount;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@MicronautTest
class CalculationDiscountTest {
    @Inject
    private CalculateDiscount calculateDiscount;

    @Inject
    private GetProductDiscount getProductDiscount;

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    @DisplayName("should calculate the product with discount")
    void testCalculateDiscount(Long productId, Long value, Float mockReturn, Float expected) {
        when(getProductDiscount.getPercent(anyLong())).thenReturn(mockReturn);

        var result = calculateDiscount.apply(productId, value);

        verify(getProductDiscount, times(1)).getPercent(anyLong());

        assertEquals(expected, result);
    }

    @MockBean(GettingProductDiscount.class)
    GetProductDiscount getProductDiscount() {
        return mock(GetProductDiscount.class);
    }
}