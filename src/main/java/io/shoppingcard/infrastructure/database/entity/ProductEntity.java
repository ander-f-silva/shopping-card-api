package io.shoppingcard.infrastructure.database.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    private Long id;
    private String title;
    private String description;
    private Long amount;
    @JsonProperty("is_gift")
    private Boolean isGift;
}