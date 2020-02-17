package com.myretail.productdemo.controller;

import com.myretail.productdemo.adapter.ProductDetailsAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired()
    private ProductDetailsAdapter productDetailsAdapter;

    @GetMapping(value = "/products/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getProduct(@PathVariable Integer id){
        return productDetailsAdapter.getProductDetails(id);
    }
}
