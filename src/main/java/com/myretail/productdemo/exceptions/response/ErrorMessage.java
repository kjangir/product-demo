package com.myretail.productdemo.exceptions.response;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ErrorMessage {
    @JsonProperty("errorMessage")
    private String errMessage;

    public ErrorMessage(final String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    @Deprecated
    public ErrorMessage() {
    }
}
