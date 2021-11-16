package io.shoppingcard.infrastructure.database.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductEntity {
    private Long id;
    private String title;
    private String description;
    private Long amount;
    @JsonProperty("is_gift")
    private Boolean isGift;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getAmount() {
        return amount;
    }

    public Boolean getGift() {
        return isGift;
    }
}