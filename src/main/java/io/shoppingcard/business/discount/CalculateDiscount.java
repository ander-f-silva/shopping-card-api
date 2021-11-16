package io.shoppingcard.business.discount;

public interface CalculateDiscount {
    Float apply(Integer productId, Float value);
}
