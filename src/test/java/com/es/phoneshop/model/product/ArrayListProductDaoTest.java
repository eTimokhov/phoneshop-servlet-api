package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testFindProductsWithResults() {
        Product product = getCorrectProduct();
        product.setId(1L);
        productDao.save(product);
        assertEquals(1, productDao.findProducts().size());
    }

    @Test
    public void testGetProduct() {
        Product product = new Product();
        product.setId(1L);
        productDao.save(product);
        assertNotNull(productDao.getProduct(1L));
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product();
        product.setId(1L);
        productDao.save(product);
        productDao.delete(1L);
        assertNull(productDao.getProduct(1L));
    }

    @Test
    public void testAddTwoProductsWithSameId() {
        Product product1 = getCorrectProduct();
        product1.setId(1L);
        productDao.save(product1);
        Product product2 = getCorrectProduct();
        product2.setId(1L);
        productDao.save(product2);
        assertEquals(1, productDao.findProducts().size());
    }

    @Test
    public void testFindProductsWithZeroStock() {
        Product product = new Product();
        product.setPrice(BigDecimal.ONE);
        productDao.save(product);
        assertEquals(0, productDao.findProducts().size());
    }

    @Test
    public void testFindProductsWithNullPrice() {
        Product product = new Product();
        product.setStock(1);
        productDao.save(product);
        assertEquals(0, productDao.findProducts().size());
    }

    private Product getCorrectProduct() {
        Product product = new Product();
        product.setStock(1);
        product.setPrice(BigDecimal.ONE);
        return product;
    }
}
