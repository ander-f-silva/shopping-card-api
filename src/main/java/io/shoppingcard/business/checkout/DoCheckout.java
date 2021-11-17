package io.shoppingcard.business.checkout;

import io.shoppingcard.business.dto.Product;
import io.shoppingcard.business.dto.ProductsAggregate;

import java.util.List;

@FunctionalInterface
public interface DoCheckout {
    ProductsAggregate apply(final List<Product> products);
}
