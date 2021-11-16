package io.sc.checkout.business;

public interface CalculateDiscount {
    Float apply(Integer productId, Float value);
}
