package io.shoppingcard.business.dto;

import lombok.*;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class ProductsAggregate {
    private Long totalAmount;

    private Long totalAmountWithDiscount;

    private Long totalDiscount;

    private List<Product> products;
}
