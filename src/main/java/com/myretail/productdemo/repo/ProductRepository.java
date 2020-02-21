package com.myretail.productdemo.repo;


import com.myretail.productdemo.model.ProductEntity;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository("productRepository")
public interface ProductRepository extends CassandraRepository<ProductEntity, MapId> {

}

