package io.shoppingcard.business.discount;

@FunctionalInterface
public interface CalculateDiscount {
    Float apply(final Long productId, final Long value);
}
