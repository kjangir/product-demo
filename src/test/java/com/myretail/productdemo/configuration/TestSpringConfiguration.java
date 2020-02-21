package com.myretail.productdemo.configuration;

import com.myretail.productdemo.adapter.ProductPriceDAO;
import com.myretail.productdemo.repo.ProductRepository;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
@Profile("unit-test")
public class TestSpringConfiguration {

    @Bean
    public CassandraOperations cassandraTemplate() {
        return Mockito.mock(CassandraOperations.class);
    }

    @Bean
    @Qualifier("getProductNameRestTemplate")
    public RestTemplate getProductNameRestTemplate() {
        return new RestTemplate();
    }

    @MockBean
    @Qualifier("productRepository")
    public ProductRepository productRepository;

    @Autowired
    @Qualifier("getProductNameRestTemplate")
    public RestTemplate restTemplate = new RestTemplate();

    @Bean
    public ProductPriceDAO getProductPriceDAO(){ return new ProductPriceDAO();}
}
