package io.shoppingcard.application;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.reactivex.Single;
import io.shoppingcard.application.payload.ProductsRequest;
import io.shoppingcard.application.payload.ProductsResponse;
import io.shoppingcard.business.checkout.DoCheckout;
import io.shoppingcard.business.dto.Product;
import io.shoppingcard.business.dto.ProductsAggregate;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/api/v1/checkout")
@Validated
@AllArgsConstructor
class CheckoutResource {
    @Inject
    private DoCheckout doCheckout;

    @Post
    public Single<ProductsResponse> apply(@Body @Valid ProductsRequest request) {
        var products = mapperPayloadRequestToDto(request);

        //TODO:valida se o produto não existe

        var productsAggregate = doCheckout.apply(products);

        var response = mapperDtoToPayloadResponse(productsAggregate);

        return Single.just(response);
    }

    private ProductsResponse mapperDtoToPayloadResponse(ProductsAggregate productsAggregate) {
        var productsResponse = productsAggregate
                .getProducts()
                .stream()
                .map(product -> new ProductsResponse.ProductResponse(
                                product.getId(),
                                product.getQuantity(),
                                product.getUnitAmount(),
                                product.getTotalAmount(),
                                product.getDiscount(),
                                product.getIsGift()
                        )
                )
                .collect(Collectors.toList());

        var response = ProductsResponse
                .builder()
                .totalAmount(productsAggregate.getTotalAmount())
                .totalAmountWithDiscount(productsAggregate.getTotalAmountWithDiscount())
                .totalDiscount(productsAggregate.getTotalDiscount())
                .products(productsResponse)
                .build();
        return response;
    }

    private List<Product> mapperPayloadRequestToDto(ProductsRequest request) {
        var products = request.getProducts()
                .stream()
                .map(productRequest ->
                        Product.builder()
                                .id(productRequest.getId())
                                .quantity(productRequest.getQuantity())
                                .build()
                )
                .collect(Collectors.toList());
        return products;
    }
}
