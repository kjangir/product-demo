package com.myretail.productdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentPrice {

    private float value;

    @JsonProperty("currency_code")
    private String currencyCode;
}
