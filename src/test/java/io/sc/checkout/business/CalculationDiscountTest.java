package io.sc.checkout.business;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@MicronautTest
class CalculationDiscountTest {
    private CalculationDiscount calculationDiscount;

    @Inject
    private GetProductDiscount getProductDiscount;

    @BeforeEach
    void setUpAll() {
        calculationDiscount = new CalculationDiscount(getProductDiscount);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    @DisplayName("should calculate the product with discount")
    void testCalculateDiscount(Integer productId, Float value, Float mockReturn, Float expected) {
        when(getProductDiscount.getPercent(anyInt())).thenReturn(mockReturn);

        var result = calculationDiscount.apply(productId, value);

        verify(getProductDiscount, times(1)).getPercent(anyInt());

        assertEquals(expected, result);
    }

    @MockBean(GettingProductDiscount.class)
    GetProductDiscount getProductDiscount() {
        return mock(GetProductDiscount.class);
    }
}