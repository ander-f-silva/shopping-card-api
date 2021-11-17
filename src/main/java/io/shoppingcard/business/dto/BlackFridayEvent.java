package io.shoppingcard.business.dto;

import java.time.LocalDate;

public class BlackFridayEvent {
    private LocalDate dateOfEvent;

    public BlackFridayEvent(LocalDate dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public Boolean isNow() {
        return LocalDate.now().isEqual(dateOfEvent);
    }
}
