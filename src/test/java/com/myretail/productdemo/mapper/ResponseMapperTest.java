package com.myretail.productdemo.mapper;

import com.myretail.productdemo.adapter.ProductPriceDAO;
import com.myretail.productdemo.configuration.TestSpringConfiguration;
import com.myretail.productdemo.model.ProductDetailResponse;
import com.myretail.productdemo.model.ProductEntity;
import com.myretail.productdemo.model.Response;
import com.myretail.productdemo.repo.ProductRepository;
import com.myretail.productdemo.util.Helper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.util.Assert;

import java.util.Optional;

@SpringJUnitConfig(TestSpringConfiguration.class)
@SpringBootTest
public class ResponseMapperTest {

    @Test
    public void testGetProductPrice(){
        ProductEntity productEntity = Helper.getObjectFromFile("mock/mapper/productEntity.json", ProductEntity.class);
        ProductDetailResponse productDetailResponse = Helper.getObjectFromFile("mock/mapper/productDetails.json", ProductDetailResponse.class);
        Response expected = Helper.getObjectFromFile("mock/mapper/responseExpected.json", Response.class);

        assert expected != null;
        assert productDetailResponse != null;
        assert productEntity != null;

        Response actual = ResponseMapper.mapResponse(productEntity, productDetailResponse);

        Assert.notNull(actual, "Response is not null");
        Helper.assertObjectsEqual(expected, actual);
    }

}