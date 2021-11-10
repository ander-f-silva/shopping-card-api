package io.sc.checkout.business;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller
public class CalculateDiscountResource {

    private GetProductDiscount calculateDiscount;

    public CalculateDiscountResource(GetProductDiscount calculateDiscount) {
        this.calculateDiscount = calculateDiscount;
    }

    @Get(produces = MediaType.TEXT_PLAIN)
    public Double calculate() {
        System.out.println("Next  service");
        return calculateDiscount.getPercent(1);
    }
}
