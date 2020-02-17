package com.myretail.productdemo.adapter;

import com.myretail.productdemo.model.ProductDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductDetailsAdapter {

    @Autowired
    @Qualifier("getProductNameRestTemplate")
    private RestTemplate restTemplate;

    @Value("${product.details.url}")
    private String url;

    public String getProductDetails(Integer id) {
        Map<String, Integer> pathVariables = new HashMap<>();
        pathVariables.put("productId", id);
        ProductDetailResponse response = restTemplate.exchange(url, HttpMethod.GET, null, ProductDetailResponse.class, pathVariables).getBody();
        return response.getProduct().getItem().getProductDescription().getTitle();
//        return response.toString();
    }
}
