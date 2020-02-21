package com.myretail.productdemo.exceptions;

import java.io.Serializable;

/**
 * Exception type to be used in case of request related external service failure.
 * When thrown this exception is not the candidate for opening the circuit breaker.
 */
public class ExternalServiceNonCircuitException extends RuntimeException implements Serializable {

    public ExternalServiceNonCircuitException(String message) {
        super(message);
    }

    public ExternalServiceNonCircuitException(String message, Throwable cause) {
        super(message, cause);
    }
}