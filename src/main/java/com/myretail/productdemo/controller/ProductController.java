package com.myretail.productdemo.controller;

import com.myretail.productdemo.model.ProductEntity;
import com.myretail.productdemo.model.Response;
import com.myretail.productdemo.service.ProductDetailsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductDetailsService productDetailsService;

    @ApiOperation(value = "GET PRODUCT DETAILS")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Product Details Response", response = Response.class),
            @ApiResponse(code = 400, message = "Validation Error"),
            @ApiResponse(code = 424, message = "Dependent Service Failed"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 502, message = "Bad Gateway")
    })
    @GetMapping(value = "/products/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DeferredResult<Response> getProduct(@PathVariable Integer id){
        DeferredResult<Response> deferredResult = new DeferredResult<>();
        productDetailsService.getProductDetails(id).subscribe(deferredResult::setResult, deferredResult::setErrorResult);
        return deferredResult;
    }

    @ApiOperation(value = "UPDATE PRODUCT")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Product Entity", response = ProductEntity.class),
            @ApiResponse(code = 400, message = "Validation Error"),
            @ApiResponse(code = 424, message = "Dependent Service Failed"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 502, message = "Bad Gateway")
    })
    @PutMapping(value = "/product", produces = {MediaType.APPLICATION_JSON_VALUE}, headers = {"Accept=application/json"})
    public DeferredResult<ProductEntity> updateProduct(@RequestBody @Valid ProductEntity productEntity){
        DeferredResult<ProductEntity> deferred = new DeferredResult<>();
        productDetailsService.createProductEntity(productEntity).subscribe(deferred::setResult, deferred::setErrorResult);
        return deferred;
    }
}
