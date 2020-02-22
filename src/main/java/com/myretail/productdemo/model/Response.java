package com.myretail.productdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    @ApiModelProperty(value = "Product Id", example = "13860427", position = 1)
    private int id;
    @ApiModelProperty(value = "Product Title", example = "Conan the Barbarian (dvd_video)", position = 2)
    private String name;

    @JsonProperty("current_price")
    @ApiModelProperty( position = 3)
    private CurrentPrice currentPrice;
}
