package io.sc.checkout.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
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