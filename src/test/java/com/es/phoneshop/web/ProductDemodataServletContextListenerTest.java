package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;

public class ProductDemodataServletContextListenerTest {

    private ArrayListProductDao productDao = ArrayListProductDao.getInstance();
    private ProductDemodataServletContextListener listener = new ProductDemodataServletContextListener();

    @Before
    public void setup() {
        productDao.setProducts(new ArrayList<>());
        listener.contextInitialized(null);
    }

    @After
    public void clearDao() {
        productDao.setProducts(new ArrayList<>());
    }

    @Test
    public void testAddProductsToDao() {
        assertNotEquals(0, productDao.findProducts().size());
    }
}