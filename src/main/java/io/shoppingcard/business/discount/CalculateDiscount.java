package io.shoppingcard.business.discount;

@FunctionalInterface
public interface CalculateDiscount {
    Float apply(Long productId, Long value);
}
