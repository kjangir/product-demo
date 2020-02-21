package com.myretail.productdemo.controller;

import com.myretail.productdemo.model.ProductDetailResponse;
import com.myretail.productdemo.model.ProductEntity;
import com.myretail.productdemo.service.ProductDetailsService;
import com.myretail.productdemo.util.Helper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {ProductController.class, ProductDetailsService.class})
@WebMvcTest
class ProductControllerTest {

@Autowired
private MockMvc mockMvc;

    @Test
    private void getProduct() throws Exception {
        ProductEntity productEntity = Helper.getObjectFromFile("mock/mapper/productEntity.json", ProductEntity.class);
        ProductDetailResponse productDetailResponse = Helper.getObjectFromFile("mock/mapper/productDetails.json", ProductDetailResponse.class);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/13860427")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String resultDOW = result.getResponse().getContentAsString();
//        assertNotNull(resultDOW);
//        assertEquals(dow, resultDOW);
    }
}