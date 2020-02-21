package com.myretail.productdemo.mapper;

import com.myretail.productdemo.model.CurrentPrice;
import com.myretail.productdemo.model.ProductDetailResponse;
import com.myretail.productdemo.model.ProductEntity;
import com.myretail.productdemo.model.Response;

public class ResponseMapper {
    public static Response mapResponse(ProductEntity productEntity, ProductDetailResponse productDetailResponse){
        return Response.builder()
                .id(productEntity.getId())
                .name(productDetailResponse.getProduct().getItem().getProductDescription().getTitle())
                .currentPrice(CurrentPrice.builder()
                        .value(productEntity.getCurrentPrice())
                        .currencyCode(productEntity.getCurrencyCode())
                        .build())
                .build();
    }
}
