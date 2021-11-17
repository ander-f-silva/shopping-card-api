package io.shoppingcard.business.checkout;

import io.shoppingcard.business.discount.CalculateDiscount;
import io.shoppingcard.business.dto.Product;
import io.shoppingcard.business.dto.ProductsAggregate;
import io.shoppingcard.infrastructure.database.repository.ProductRepository;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
@AllArgsConstructor
public class DoingCheckout implements DoCheckout {
    private ProductRepository productRepository;

    private CalculateDiscount calculateDiscount;

    public ProductsAggregate apply(final List<Product> products) {
        var productsComplete = products
                .stream()
                .map(this::completeDataOfCheckoutToProduct)
                .collect(Collectors.toList());

        var totalAmount = productsComplete.stream().mapToLong(Product::getTotalAmount).sum();

        var totalDiscount = productsComplete.stream().mapToLong(product -> product.getDiscount().longValue()).sum();

        var totalAmountWithDiscount = totalAmount - totalDiscount;

        return new ProductsAggregate(totalAmount, totalAmountWithDiscount, totalDiscount, productsComplete);
    }

    private Product completeDataOfCheckoutToProduct(Product product) {
        var productEntity = productRepository.findById(product.getId());

        return Product
                .builder()
                .id(productEntity.getId())
                .discount(calculateDiscount.apply(productEntity.getId(), productEntity.getAmount()).longValue())
                .quantity(product.getQuantity())
                .unitAmount(productEntity.getAmount())
                .totalAmount(calculateTotalAmount(productEntity.getAmount(), product.getQuantity()))
                .isGift(productEntity.getIsGift())
                .build();
    }

    private Long calculateTotalAmount(Long amount, Integer quantity) {
        return amount * quantity;
    }
}
