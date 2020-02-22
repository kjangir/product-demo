package com.myretail.productdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentPrice {

    @ApiModelProperty(required = true, value = "Product Current Price", example = "21.11", position = 1)
    private float value;

    @JsonProperty("currency_code")
    @ApiModelProperty(value = "Product Price Currency Code ", example = "USD",
            notes = "Default value is USD", position = 2)
    @Builder.Default
    private String currencyCode = "USD";
}
