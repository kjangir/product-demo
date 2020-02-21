package com.myretail.productdemo.exceptions;

import com.myretail.productdemo.exceptions.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Base abstract class for handling and processing all of the service exceptions.Each Service must inherit this class and override handleExceptionInternal if it needs specific exception processing mechanism.
 */
@ControllerAdvice
public class CasCoreServiceExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CasCoreServiceExceptionHandler.class);

    @InitBinder
    private void activateDirectFieldAccess(DataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }

    protected ResponseEntity<ErrorResponse> handleExceptionInternalOverride(Exception ex) {
        return null;
    }

    @ExceptionHandler()
    @ResponseBody
    protected final ResponseEntity<ErrorResponse> handleExceptionInternal(Exception ex, HttpServletRequest request) {

        ResponseEntity<ErrorResponse> responseEntity = handleExceptionInternalOverride(ex);
        if (responseEntity == null) {
            List<String> errorMessages = null;
            HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
            if (ex instanceof MethodArgumentNotValidException) {
                BindingResult result = ((MethodArgumentNotValidException) ex).getBindingResult();
                List<ObjectError> allErrors = result.getAllErrors();
                errorMessages = allErrors.stream().map(x -> {
                    if (x instanceof FieldError)
                        return ((FieldError) x).getField() + " " + x.getDefaultMessage();
                    return x.getDefaultMessage();
                }).collect(Collectors.toList());

                statusCode = HttpStatus.BAD_REQUEST;

            } else if (ex instanceof HttpMessageNotReadableException
                    || ex instanceof ValidationException
                    || ex instanceof ServletRequestBindingException
                    || ex instanceof BindException
                    || ex instanceof MethodArgumentTypeMismatchException) {
                errorMessages = new ArrayList<>(Collections.singletonList(ex.getMessage()));
                statusCode = HttpStatus.BAD_REQUEST;

            }  else if (ex instanceof ConstraintViolationException) {
                Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) ex).getConstraintViolations();
                errorMessages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());

                statusCode = HttpStatus.BAD_REQUEST;
            } else if (ex instanceof ExternalServiceNonCircuitException) {
                statusCode = HttpStatus.FAILED_DEPENDENCY;
            } else if (ex instanceof ExternalServiceRuntimeException) {
                statusCode = HttpStatus.BAD_GATEWAY;
            } else { // any other unhandled exceptions, sanitize the message to prevent error details leak.
                errorMessages = new ArrayList<>(Collections.singletonList("Internal Server Error. Please contact support team."));
            }


            ErrorResponse errorResponse = new ErrorResponse(errorMessages);
            responseEntity = new ResponseEntity<>(errorResponse, new HttpHeaders(), statusCode);
        }
        // Do not log validation exceptions
        if (responseEntity.getStatusCode().value() > HttpStatus.BAD_REQUEST.value()) {
            LOGGER.error("Error in service:", ex);
        }


        return responseEntity;
    }

}
