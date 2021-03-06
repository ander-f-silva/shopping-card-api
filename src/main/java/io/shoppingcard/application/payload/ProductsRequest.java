package io.shoppingcard.application.payload;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Introspected
@ToString
public class ProductsRequest {
    @NotNull
    @Valid
    private List<ProductRequest> products;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Introspected
    public static class ProductRequest {
        @NotNull
        private Long id;
        @NotNull
        @Positive
        private Integer quantity;
    }
}
