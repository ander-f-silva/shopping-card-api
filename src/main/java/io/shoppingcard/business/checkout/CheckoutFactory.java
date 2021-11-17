package io.shoppingcard.business.checkout;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import io.shoppingcard.business.dto.BlackFridayEvent;
import jakarta.inject.Singleton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Factory
class CheckoutFactory {
    @Value("dateBlackFriday")
    private String dateBlackFriday;

    @Singleton
    public BlackFridayEvent blackFridayEvent() {
        var blackFriday = LocalDate.parse(dateBlackFriday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return new BlackFridayEvent(blackFriday);
    }
}
