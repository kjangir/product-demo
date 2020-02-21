package com.myretail.productdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private int id;

    private String name;

    @JsonProperty("current_price")
    private CurrentPrice currentPrice;
}
