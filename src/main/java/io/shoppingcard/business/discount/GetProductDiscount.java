package io.shoppingcard.business.discount;

@FunctionalInterface
public interface GetProductDiscount {
    Float getPercent(final Long productId);
}
