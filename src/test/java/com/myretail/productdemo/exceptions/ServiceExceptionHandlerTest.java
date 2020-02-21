package com.myretail.productdemo.exceptions;

import com.myretail.productdemo.configuration.TestSpringConfiguration;
import com.myretail.productdemo.exceptions.response.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.Set;

@SpringJUnitConfig(TestSpringConfiguration.class)
@SpringBootTest
class ServiceExceptionHandlerTest {

    @Test
    public void handleConstraintViolationExceptionTest() {
        ServiceExceptionHandler handler = new ServiceExceptionHandler();

        Set<ConstraintViolation<?>> constraintViolations = new HashSet<>();
        ConstraintViolationException ex = new ConstraintViolationException("Exception Test", constraintViolations);
        ResponseEntity<ErrorResponse> responseEntity = handler.handleExceptionInternal(ex, null);

        Assert.isTrue(responseEntity.getStatusCode().is4xxClientError(), "Exception");
    }

    @Test
    public void handleValidationExceptionTest() {
        ServiceExceptionHandler handler = new ServiceExceptionHandler();
        ValidationException ex = new ValidationException();
        ResponseEntity<ErrorResponse> responseEntity = handler.handleExceptionInternal(ex, null);

        Assert.isTrue(responseEntity.getStatusCode().is4xxClientError(), "Exception");
    }

    @Test
    public void handleExternalServiceNonCircuitExceptionTest() {
        ServiceExceptionHandler handler = new ServiceExceptionHandler();
        ResourceNotFoundException ex = new ResourceNotFoundException("ExternalServiceNonCircuitException");
        ResponseEntity<ErrorResponse> responseEntity = handler.handleExceptionInternal(ex, null);

        Assert.isTrue(responseEntity.getStatusCode().is4xxClientError(), "Exception");
    }

    @Test
    public void handleExternalServiceRuntimeExceptionTest() {
        ServiceExceptionHandler handler = new ServiceExceptionHandler();
        ExternalServiceRuntimeException ex = new ExternalServiceRuntimeException("ExternalServiceRuntimeException");
        ResponseEntity<ErrorResponse> responseEntity = handler.handleExceptionInternal(ex, null);

        Assert.isTrue(responseEntity.getStatusCode().is5xxServerError(), "Exception");
    }
}