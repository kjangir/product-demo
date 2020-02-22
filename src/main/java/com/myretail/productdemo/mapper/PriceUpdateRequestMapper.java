package com.myretail.productdemo.mapper;

import com.myretail.productdemo.model.CurrentPrice;
import com.myretail.productdemo.model.ProductEntity;

public class PriceUpdateRequestMapper {
    public static ProductEntity map(CurrentPrice currentPrice, int id, String clientId){
        return ProductEntity.builder()
                .id(id)
                .currentPrice(currentPrice.getValue())
                .currencyCode(currentPrice.getCurrencyCode())
                .updatedBy(clientId)
                .build();
    }
}
