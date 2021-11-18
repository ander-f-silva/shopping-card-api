package io.shoppingcard.business.checkout;

import io.shoppingcard.business.dto.Product;
import io.shoppingcard.business.dto.ProductsAggregate;

import java.util.List;

public interface DoCheckout {
    ProductsAggregate apply(final List<Product> products);

    void validateEntryOfProduct(final List<Product> products);
}
