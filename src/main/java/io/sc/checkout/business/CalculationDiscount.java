package io.sc.checkout.business;

import jakarta.inject.Singleton;

//TODO: Add logger to debug
@Singleton
class CalculationDiscount implements CalculateDiscount {
    private static final Float DISCOUNT_ZERO = 0F;

    private GetProductDiscount getProductDiscount;

    public CalculationDiscount(GetProductDiscount getProductDiscount) {
        this.getProductDiscount = getProductDiscount;
    }

    public Float apply(Integer productId, Float value) {
        var discountPercent = getProductDiscount.getPercent(productId);

        if (discountPercent.equals(DISCOUNT_ZERO))
            return DISCOUNT_ZERO;

        return value / discountPercent;
    }
}
