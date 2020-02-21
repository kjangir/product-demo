package com.myretail.productdemo.exceptions.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {

    @JsonProperty("errors")
    @ApiModelProperty(
            value = "List of Error Messages")
    private final List<ErrorMessage> errors = new ArrayList<>();

    public ErrorResponse(final List<String> errorMessages) {
        errorMessages.forEach(err -> this.errors.add(new ErrorMessage(err)));
    }

    public List<ErrorMessage> getErrors() {
        return errors;
    }

    @Deprecated
    public ErrorResponse() {
    }
}
