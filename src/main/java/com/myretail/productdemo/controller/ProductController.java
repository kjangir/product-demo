package com.myretail.productdemo.controller;

import com.myretail.productdemo.exceptions.response.ErrorResponse;
import com.myretail.productdemo.model.CurrentPrice;
import com.myretail.productdemo.model.ProductEntity;
import com.myretail.productdemo.model.Response;
import com.myretail.productdemo.service.ProductDetailsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductDetailsService productDetailsService;

    @ApiOperation(value = "GET PRODUCT DETAILS")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Product Details Response", response = Response.class),
            @ApiResponse(code = 400, message = "Validation Error", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class),
            @ApiResponse(code = 502, message = "Bad Gateway", response = ErrorResponse.class)
    })
    @GetMapping(value = "/products/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DeferredResult<Response> getProduct(@PathVariable @ApiParam(value = "Product Id", example = "13860427") Integer id) {
        DeferredResult<Response> deferredResult = new DeferredResult<>();
        productDetailsService.getProductDetails(id).subscribe(deferredResult::setResult, deferredResult::setErrorResult);
        return deferredResult;
    }

    @ApiOperation(value = "ADD PRODUCT PRICE")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Product Entity Created", response = ProductEntity.class),
            @ApiResponse(code = 400, message = "Validation Error", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class),
            @ApiResponse(code = 502, message = "Bad Gateway", response = ErrorResponse.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/products", produces = {MediaType.APPLICATION_JSON_VALUE}, headers = {"Accept=application/json"})
    public DeferredResult<ResponseEntity<ProductEntity>> createProduct(@RequestBody @Valid ProductEntity productEntity,
                                                        @RequestHeader(name = "client-id")@ApiParam(value = "Client Id", example = "SYSTEM") String clientId) {
        DeferredResult<ResponseEntity<ProductEntity>> deferred = new DeferredResult<>();
        productEntity.setCreatedBy(clientId);
        productEntity.setUpdatedBy(clientId);
        productDetailsService.createProductEntity(productEntity)
                .subscribe(response -> deferred.setResult(new ResponseEntity<>(response, HttpStatus.CREATED)),
                        deferred::setErrorResult);
        return deferred;
    }

    @ApiOperation(value = "UPDATE PRODUCT PRICE")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Product Entity Updated", response = ProductEntity.class),
            @ApiResponse(code = 400, message = "Validation Error", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Resource Not Found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class),
            @ApiResponse(code = 502, message = "Bad Gateway", response = ErrorResponse.class)
    })
    @PutMapping(value = "/products/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, headers = {"Accept=application/json"})
    public DeferredResult<ProductEntity> updateProduct(@RequestBody @Valid CurrentPrice currentPrice,
                                                       @PathVariable @ApiParam(value = "Product Id", example = "13860427") Integer id,
                                                       @RequestHeader(name = "client-id")@ApiParam(value = "Client Id", example = "SYSTEM") String clientId) {
        DeferredResult<ProductEntity> deferred = new DeferredResult<>();
        productDetailsService.updateProductPrice(currentPrice, id, clientId).subscribe(deferred::setResult, deferred::setErrorResult);
        return deferred;
    }

    @ApiOperation(value = "DELETE PRODUCT PRICE")
    @ApiResponses({
            @ApiResponse(code = 204, message = "No Content", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Validation Error", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class),
            @ApiResponse(code = 502, message = "Bad Gateway", response = ErrorResponse.class)
    })
    @DeleteMapping(value = "/products/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, headers = {"Accept=application/json"})
    public DeferredResult<ResponseEntity> deleteProduct(@PathVariable @ApiParam(value = "Product Id", example = "13860427") Integer id) {
        DeferredResult<ResponseEntity> deferred = new DeferredResult<>();
        productDetailsService.deleteProductPrice(id)
                .subscribe(response -> deferred.setResult(new ResponseEntity(HttpStatus.NO_CONTENT)),
                        deferred::setErrorResult);
        return deferred;
    }
}
