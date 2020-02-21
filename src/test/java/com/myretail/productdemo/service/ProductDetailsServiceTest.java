package com.myretail.productdemo.service;

import com.myretail.productdemo.adapter.ProductDetailsAdapter;
import com.myretail.productdemo.adapter.ProductPriceDAO;
import com.myretail.productdemo.configuration.TestSpringConfiguration;
import com.myretail.productdemo.exceptions.ExternalServiceRuntimeException;
import com.myretail.productdemo.model.ProductDetailResponse;
import com.myretail.productdemo.model.ProductEntity;
import com.myretail.productdemo.model.Response;
import com.myretail.productdemo.util.Helper;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(TestSpringConfiguration.class)
@SpringBootTest
class ProductDetailsServiceTest {

    @Mock
    private ProductDetailsAdapter productDetailsAdapter;

    @Mock
    private ProductPriceDAO productPriceDAO;

    @InjectMocks
    private ProductDetailsService productDetailsService = new ProductDetailsService();

    @Test
    void getProductDetails() {

        int id = 13860427;
        ProductEntity productEntity = Helper.getObjectFromFile("mock/mapper/productEntity.json", ProductEntity.class);
        ProductDetailResponse productDetailResponse = Helper.getObjectFromFile("mock/mapper/productDetails.json", ProductDetailResponse.class);
        Response expected = Helper.getObjectFromFile("mock/mapper/responseExpected.json", Response.class);

        Mockito.doReturn(productEntity).when(productPriceDAO).getProductPrice(id);
        Mockito.doReturn(productDetailResponse).when(productDetailsAdapter).getProductDetails(id);

        TestObserver<Response> testObserver = productDetailsService.getProductDetails(id).test();
        testObserver.awaitTerminalEvent();

        testObserver.assertNoErrors();
        testObserver.assertValue(actual-> Helper.objectsEquals(expected, actual));

    }

    @Test
    void createProductEntity() {

        ProductEntity productEntity = Helper.getObjectFromFile("mock/mapper/productEntity.json", ProductEntity.class);

        Mockito.doReturn(productEntity).when(productPriceDAO).saveProductPrice(productEntity);

        TestObserver<ProductEntity> testObserver = productDetailsService.createProductEntity(productEntity).test();
        testObserver.awaitTerminalEvent();

        testObserver.assertNoErrors();
        testObserver.assertValue(actual-> Helper.objectsEquals(productEntity, actual));

    }

    @Test
    void createProductEntity_WithException() {

        ProductEntity productEntity = Helper.getObjectFromFile("mock/mapper/productEntity.json", ProductEntity.class);

        Mockito.doThrow(new ExternalServiceRuntimeException("DATABASE NOT AVAILABLE")).when(productPriceDAO).saveProductPrice(productEntity);

        TestObserver<ProductEntity> testObserver = productDetailsService.createProductEntity(productEntity).test();
        testObserver.awaitTerminalEvent();

        testObserver.assertError(ExternalServiceRuntimeException.class);

    }

}