package com.myretail.productdemo.adapter;

import com.myretail.productdemo.configuration.TestSpringConfiguration;
import com.myretail.productdemo.exceptions.ResourceNotFoundException;
import com.myretail.productdemo.model.ProductEntity;
import com.myretail.productdemo.repo.ProductRepository;
import com.myretail.productdemo.util.Helper;
import org.junit.jupiter.api.Assertions;
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
public class ProductPriceDAOTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductPriceDAO productPriceDAO = new ProductPriceDAO();

    @Test
    public void testGetProductPrice(){
        ProductEntity expected = Helper.getObjectFromFile("mock/adapter/productEntity.json", ProductEntity.class);
        assert expected != null;
        Mockito.doReturn(Optional.of(expected)).when(productRepository).findById(Mockito.any());
        ProductEntity actual = productPriceDAO.getProductPrice(13860427);
        Assert.notNull(actual, "Response is not null");
        Helper.assertObjectsEqual(expected, actual);
    }

    @Test
    public void testGetProductPrice_WithNoResult(){
        Mockito.doReturn(Optional.empty()).when(productRepository).findById(Mockito.any());
        ProductEntity actual = productPriceDAO.getProductPrice(13860427);
        Assert.isNull(actual, "Response is null");
    }

    @Test
    public void testSaveProductEntity(){
        ProductEntity actual = Helper.getObjectFromFile("mock/adapter/productEntity.json", ProductEntity.class);
        assert actual != null;
        Mockito.doReturn(actual).when(productRepository).save(Mockito.any());
        ProductEntity response = productPriceDAO.saveProductPrice(actual);
        Assert.notNull(response, "Response is not null");
        Helper.assertObjectsEqual(response, actual);
    }

    @Test
    public void testUpdateProductPrice() throws ResourceNotFoundException {
        ProductEntity actual = Helper.getObjectFromFile("mock/adapter/productEntity.json", ProductEntity.class);
        assert actual != null;
        Mockito.doReturn(true).when(productRepository).existsById(Mockito.any());
        Mockito.doReturn(actual).when(productRepository).save(Mockito.any());
        ProductEntity response = productPriceDAO.updateProductPrice(actual);
        Assert.notNull(response, "Response is not null");
        Helper.assertObjectsEqual(response, actual);
    }

    @Test
    public void testUpdateProductPrice_404() throws ResourceNotFoundException {
        ProductEntity actual = Helper.getObjectFromFile("mock/adapter/productEntity.json", ProductEntity.class);
        assert actual != null;
        Mockito.doReturn(false).when(productRepository).existsById(Mockito.any());
        Assertions.assertThrows(ResourceNotFoundException.class, ()->productPriceDAO.updateProductPrice(actual));
    }

    @Test
    public void testDeleteProductPrice() {
        int id = 13860427;
        Mockito.doNothing().when(productRepository).deleteById(Mockito.any());
        productPriceDAO.deleteProductPrice(id);
    }

}