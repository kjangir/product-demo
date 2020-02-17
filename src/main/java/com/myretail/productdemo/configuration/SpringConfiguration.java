package com.myretail.productdemo.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfiguration {

    @Bean
    @Qualifier("getProductNameRestTemplate")
    public RestTemplate getProductNameRestTemplate() {
        return createRestTemplate();
    }

    private RestTemplate createRestTemplate() {
        System.clearProperty("javax.net.ssl.trustStore");
        System.clearProperty("javax.net.ssl.trustStorePassword");
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(
                new SimpleClientHttpRequestFactory()));
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//        restTemplate.setErrorHandler();
        return restTemplate;
    }
}
