package io.shoppingcard.business.dto;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private Integer quantity;
    private Long unitAmount;
    private Long totalAmount;
    private Number discount;
    private Boolean isGift;
}
