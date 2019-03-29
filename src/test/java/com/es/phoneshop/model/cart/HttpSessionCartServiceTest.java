package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {

    private static HttpSessionCartService cartService = HttpSessionCartService.getInstance();
    private Product product = new Product();

    @Mock
    private ProductDao productDao;

    @Before
    public void setup() throws ProductNotFoundException {
        when(productDao.getProduct(anyLong())).thenReturn(product);
        cartService.setProductDao(productDao);
    }

    @After
    public void clear() {
        cartService.setCart(new Cart());
    }

    @Test
    public void testAddingProduct() throws ProductNotFoundException {
        cartService.add(3, 5);
        assertEquals(1, cartService.getCart().getCartItems().size());
    }

}