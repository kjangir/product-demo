package com.myretail.productdemo.service;

import com.myretail.productdemo.adapter.ProductDetailsAdapter;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailsService {

    @Autowired()
    private ProductDetailsAdapter productDetailsAdapter;

    public Observable<String> getProductDetails(Integer productId){
        return Observable.just(productDetailsAdapter.getProductDetails(productId));
    }
}
