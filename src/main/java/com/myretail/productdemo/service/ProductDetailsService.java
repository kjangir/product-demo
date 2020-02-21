package com.myretail.productdemo.service;

import com.myretail.productdemo.adapter.ProductPriceDAO;
import com.myretail.productdemo.adapter.ProductDetailsAdapter;
import com.myretail.productdemo.mapper.ResponseMapper;
import com.myretail.productdemo.model.ProductEntity;
import com.myretail.productdemo.model.Response;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailsService {

    @Autowired()
    private ProductDetailsAdapter productDetailsAdapter;

    @Autowired()
    private ProductPriceDAO productPriceDAO;

    public Observable<Response> getProductDetails(Integer productId){

        return Observable.zip(
                Observable.fromCallable(()->productDetailsAdapter.getProductDetails(productId)).subscribeOn(Schedulers.newThread()),
                Observable.fromCallable(()->productPriceDAO.getProductPrice(productId)).subscribeOn(Schedulers.newThread()),
                (productDetail, productEntity) ->
                        ResponseMapper.mapResponse(productEntity, productDetail));
    }

    public Observable<ProductEntity> createProductEntity(ProductEntity productEntity){
        return Observable.fromCallable(()->productPriceDAO.saveProductPrice(productEntity)).subscribeOn(Schedulers.newThread());
    }

}
