package com.myretail.productdemo.exceptions.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    @JsonProperty("errors")
    @ApiModelProperty(
            value = "Error Message")
    String errorMessage;

}
