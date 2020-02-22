package com.myretail.productdemo.service;

import com.myretail.productdemo.adapter.ProductDetailsAdapter;
import com.myretail.productdemo.adapter.ProductPriceDAO;
import com.myretail.productdemo.configuration.TestSpringConfiguration;
import com.myretail.productdemo.exceptions.ExternalServiceRuntimeException;
import com.myretail.productdemo.exceptions.ResourceNotFoundException;
import com.myretail.productdemo.model.CurrentPrice;
import com.myretail.productdemo.model.ProductDetailResponse;
import com.myretail.productdemo.model.ProductEntity;
import com.myretail.productdemo.model.Response;
import com.myretail.productdemo.util.Helper;
import io.reactivex.observers.TestObserver;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

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
        testObserver.assertValue(actual -> Helper.objectsEquals(expected, actual));

    }

    @Test
    void createProductEntity() {

        ProductEntity productEntity = Helper.getObjectFromFile("mock/service/productEntity.json", ProductEntity.class);

        Mockito.doReturn(productEntity).when(productPriceDAO).saveProductPrice(productEntity);

        TestObserver<ProductEntity> testObserver = productDetailsService.createProductEntity(productEntity).test();
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertValue(actual -> Helper.objectsEquals(productEntity, actual));

    }

    @Test
    void createProductEntity_WithException() {

        ProductEntity productEntity = Helper.getObjectFromFile("mock/service/productEntity.json", ProductEntity.class);

        Mockito.doThrow(new ExternalServiceRuntimeException("DATABASE NOT AVAILABLE")).when(productPriceDAO).saveProductPrice(productEntity);

        TestObserver<ProductEntity> testObserver = productDetailsService.createProductEntity(productEntity).test();
        testObserver.awaitTerminalEvent();
        testObserver.assertError(ExternalServiceRuntimeException.class);

    }


    @Test
    void updateProductEntity() throws ResourceNotFoundException {
        int id = 13860427;
        CurrentPrice currentPrice = Helper.getObjectFromFile("mock/service/currentPrice.json", CurrentPrice.class);
        ProductEntity productEntity = Helper.getObjectFromFile("mock/service/productEntity.json", ProductEntity.class);

        Mockito.doReturn(productEntity).when(productPriceDAO).updateProductPrice(Mockito.any());

        TestObserver<ProductEntity> testObserver = productDetailsService.updateProductPrice(currentPrice, id, "LOCAL").test();
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
        testObserver.assertValue(actual -> Helper.objectsEquals(productEntity, actual));
    }

    @Test
    void deleteProductEntity() {
        int id = 13860427;
        Mockito.doNothing().when(productPriceDAO).deleteProductPrice(id);

        TestObserver<Void> testObserver = productDetailsService.deleteProductPrice(id).test();
        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors();
    }
}