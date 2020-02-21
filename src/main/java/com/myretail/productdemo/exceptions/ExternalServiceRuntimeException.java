package com.myretail.productdemo.exceptions;

import java.io.Serializable;

/**
 * Exception type to be used in case of fatal external service failure.
 * When thrown this exception is the candidate for opening the circuit breaker.
 */
public class ExternalServiceRuntimeException extends RuntimeException implements Serializable {

    public ExternalServiceRuntimeException(String message) {
        super(message);
    }

    public ExternalServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
