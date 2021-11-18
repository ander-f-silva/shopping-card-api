package io.shoppingcard.application.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ProductsResponse {
    @JsonProperty("total_amount")
    private Long totalAmount;

    @JsonProperty("total_amount_with_discount")
    private Long totalAmountWithDiscount;

    @JsonProperty("total_discount")
    private Long totalDiscount;

    private List<ProductResponse> products;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class ProductResponse {
        private Long id;
        private Integer quantity;
        @JsonProperty("unit_amount")
        private Long unitAmount;
        @JsonProperty("total_amount")
        private Long totalAmount;
        private Long discount;
        @JsonProperty("is_gift")
        private Boolean isGift;
    }
}
