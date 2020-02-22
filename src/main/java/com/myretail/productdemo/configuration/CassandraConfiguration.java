package com.myretail.productdemo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.io.File;

@Configuration
@EnableCassandraRepositories(basePackages = "com.myretail.productdemo.repo")
public class CassandraConfiguration extends AbstractCassandraConfiguration {

    @Bean
    public CassandraClusterFactoryBean clusterFactoryBean(){
        File filePath = new File( "cassandra_truststore.jks");
        System.setProperty("javax.net.ssl.trustStore", filePath.getAbsolutePath());
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

    @Override
    protected String getKeyspaceName() {
        return "mr_product";
    }
}
