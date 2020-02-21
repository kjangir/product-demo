package com.myretail.productdemo.adapter;

import com.myretail.productdemo.configuration.TestSpringConfiguration;
import com.myretail.productdemo.exceptions.ExternalServiceExceptionHandler;
import com.myretail.productdemo.exceptions.ResourceNotFoundException;
import com.myretail.productdemo.exceptions.ExternalServiceRuntimeException;
import com.myretail.productdemo.model.ProductDetailResponse;
import com.myretail.productdemo.util.Helper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.module.spring.commons.config.AsyncConfig.withTimeout;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@SpringJUnitConfig(TestSpringConfiguration.class)
@RestClientTest(ProductDetailsAdapter.class)
public class ProductDetailsAdapterTest {

    private MockRestServiceServer mockServer;

    @Qualifier("getProductNameRestTemplate")
    private RestTemplate getProductNameRestTemplate = new RestTemplate();

    @Value("${product.details.url}")
    private String url;

    @InjectMocks
    private ProductDetailsAdapter productDetailsAdapter = new ProductDetailsAdapter();

    @BeforeEach
    public void setup() {
        getProductNameRestTemplate.setErrorHandler(new ExternalServiceExceptionHandler());
        MockitoAnnotations.initMocks(this);
        RestAssuredMockMvc.config().asyncConfig(withTimeout(100, TimeUnit.SECONDS));
        this.mockServer = MockRestServiceServer.createServer(this.getProductNameRestTemplate);
        ReflectionTestUtils.setField(productDetailsAdapter, "getProductNameRestTemplate", this.getProductNameRestTemplate);
        ReflectionTestUtils.setField(productDetailsAdapter, "url", url);
    }

    @Test
    public void testGetProductDetails(){

        int id = 13860427;
        ProductDetailResponse expected = Helper.getObjectFromFile("mock/adapter/productDetailsExpected.json", ProductDetailResponse.class);
        String mockResponse = Helper.loadJson("mock/adapter/productDetails.json", this.getClass().getClassLoader());

        Map<String, Integer> pathVariables = new HashMap<>();
        pathVariables.put("productId", id);
        String clientEndpointUri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(pathVariables).toUriString();

        assert expected != null;
        this.mockServer.expect(requestTo(clientEndpointUri)).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(mockResponse, MediaType.APPLICATION_JSON));
        ProductDetailResponse actual = productDetailsAdapter.getProductDetails(id);
        Assert.notNull(actual, "Response is not null");
        Helper.assertObjectsEqual(expected, actual);
    }

    @Test
    public void testGetProductDetails_4XX_Response(){

        int id = 13860427;
        Map<String, Integer> pathVariables = new HashMap<>();
        pathVariables.put("productId", id);
        String clientEndpointUri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(pathVariables).toUriString();

        this.mockServer.expect(requestTo(clientEndpointUri)).andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> { productDetailsAdapter.getProductDetails(id); });

    }

    @Test
    public void testGetProductDetails_5XX_Response(){

        int id = 13860427;
        Map<String, Integer> pathVariables = new HashMap<>();
        pathVariables.put("productId", id);
        String clientEndpointUri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(pathVariables).toUriString();

        this.mockServer.expect(requestTo(clientEndpointUri)).andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        Assertions.assertThrows(ExternalServiceRuntimeException.class, () -> { productDetailsAdapter.getProductDetails(id); });

    }

}