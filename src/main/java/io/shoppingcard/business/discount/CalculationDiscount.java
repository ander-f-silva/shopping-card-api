package io.shoppingcard.business.discount;

import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

@Singleton
@AllArgsConstructor
class CalculationDiscount implements CalculateDiscount {
    private static final Float DISCOUNT_ZERO = 0F;

    private GetProductDiscount getProductDiscount;

    public Float apply(final Long productId, final Long value) {
        var discountPercent = getProductDiscount.getPercent(productId);

        if (discountPercent.equals(DISCOUNT_ZERO))
            return DISCOUNT_ZERO;

        return value * discountPercent;
    }
}
