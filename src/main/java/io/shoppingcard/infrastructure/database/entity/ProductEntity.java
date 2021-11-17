package io.shoppingcard.infrastructure.database.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class ProductEntity {
    private Long id;
    private String title;
    private String description;
    private Long amount;
    @JsonProperty("is_gift")
    private Boolean isGift;
}