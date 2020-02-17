package com.myretail.productdemo.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CassandraConfiguration {

    @Bean
    public CassandraClusterFactoryBean clusterFactoryBean(){
        System.setProperty("javax.net.ssl.trustStore", "C:/Users/kjang/Downloads/cassandra_truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "amazon");
        final CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints("cassandra.us-east-2.amazonaws.com");
        cluster.setPort(9142);
        cluster.setUsername("kj-at-819758068027");
        cluster.setPassword("mfnR0OrJDxEQR1VN/UXa+FJW+VStOHUMuN8dxn0F25Q=");
        cluster.setJmxReportingEnabled(false);
        cluster.setSslEnabled(true);
        return cluster;
    }
    @Bean
    public CassandraSessionFactoryBean session() {
        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
        session.setCluster(clusterFactoryBean().getObject());
        session.setKeyspaceName("mr_product");
        session.setConverter(new MappingCassandraConverter(new CassandraMappingContext()));
        session.setSchemaAction(SchemaAction.NONE);
        return session;
    }
    @Bean
    public CassandraOperations cassandraTemplate() {
        return new CassandraTemplate(session().getObject());
    }
}
