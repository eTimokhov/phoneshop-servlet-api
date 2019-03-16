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
        Product product = new Product();
        product.setId(1L);
        product.setPrice(BigDecimal.ONE);
        product.setStock(1);
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
}
