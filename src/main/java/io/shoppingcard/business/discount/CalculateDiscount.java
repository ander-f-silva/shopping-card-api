package io.shoppingcard.business.discount;

@FunctionalInterface
public interface CalculateDiscount {
    Float apply(Integer productId, Float value);
}
