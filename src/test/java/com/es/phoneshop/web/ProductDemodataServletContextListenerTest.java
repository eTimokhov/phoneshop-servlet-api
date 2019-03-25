package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class ProductDemodataServletContextListenerTest {

    @Test
    public void testAddProductsToDao() {
        assertNotEquals(0, ArrayListProductDao.getInstance().findProducts());
    }
}