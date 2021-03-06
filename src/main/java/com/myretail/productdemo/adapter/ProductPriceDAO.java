package com.myretail.productdemo.adapter;

import com.myretail.productdemo.exceptions.ResourceNotFoundException;
import com.myretail.productdemo.model.ProductEntity;
import com.myretail.productdemo.repo.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductPriceDAO {

    @Autowired
    @Qualifier("productRepository")
    private ProductRepository productRepository;

    Logger logger = LoggerFactory.getLogger(ProductPriceDAO.class);

    public ProductEntity getProductPrice(Integer id) {
        MapId mapId = BasicMapId.id("id", id);
        logger.info("Executing Query to retrieve product price Details: {}", id);
        ProductEntity productEntity = productRepository.findById(mapId).orElse(null);
        logger.info("Retrieved results from Query: {}", id);
        return productEntity;
    }

    public ProductEntity saveProductPrice(ProductEntity entity) {
        logger.info("Executing Query to Save Product Entity : {}", entity);
        return productRepository.save(entity);
    }

    public void deleteProductPrice(int id) {
        logger.info("Executing Query to Delete Product Entity : {}", id);
        productRepository.deleteById(BasicMapId.id("id", id));
    }

    public ProductEntity updateProductPrice(ProductEntity entity) throws ResourceNotFoundException {
        logger.info("Executing Query to Update Product Price : {}", entity);
        Optional<ProductEntity> productEntity = productRepository.findById(BasicMapId.id("id",entity.getId()));
        if(productEntity.isPresent()) {
            entity.setCreatedBy(productEntity.get().getCreatedBy());
            entity.setCreatedTime(productEntity.get().getCreatedTime());
            return productRepository.save(entity);
        }
        throw new ResourceNotFoundException("Entity not found for ID : " + entity.getId());
    }
}
