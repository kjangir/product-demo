package com.myretail.productdemo.exceptions;

import java.io.Serializable;

/**
 * Exception type to be used in case of request related external service failure.
 * When thrown this exception is not the candidate for opening the circuit breaker.
 */
public class ResourceNotFoundException extends Exception implements Serializable {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}