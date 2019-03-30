package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {

    private static HttpSessionCartService cartService = HttpSessionCartService.getInstance();
    private static final String SESSION_CART_KEY = "sessionCart";
    private Product product;

    @Mock
    private ProductDao productDao;
    @Mock
    private HttpServletRequest existingSessionRequest;
    @Mock
    private HttpSession existingSession;

    @Before
    public void setup() throws ProductNotFoundException {
        cartService.setProductDao(productDao);
    }

    @Test
    public void testGetCartReturnExistingCart() {
        Cart existingCart = new Cart();
        when(existingSessionRequest.getSession()).thenReturn(existingSession);
        when(existingSession.getAttribute(SESSION_CART_KEY)).thenReturn(existingCart);

        Cart cart = cartService.getCart(existingSessionRequest);
        assertEquals(existingCart, cart);
    }

    @Test
    public void testAddDifferentProducts() throws ProductNotFoundException, OutOfStockException {
        Cart cart = new Cart();

        Product product1 = new Product();
        product1.setId(1L);
        product1.setStock(5);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setStock(30);

        when(productDao.getProduct(1L)).thenReturn(product1);
        when(productDao.getProduct(2L)).thenReturn(product2);

        cartService.add(cart, 1, 5);
        cartService.add(cart, 2, 10);

        assertEquals(2, cart.getCartItems().size());
    }

    @Test
    public void testAddSameProduct() throws ProductNotFoundException, OutOfStockException {
        Cart cart = new Cart();

        Product product = new Product();
        product.setId(1L);
        product.setStock(30);

        when(productDao.getProduct(1L)).thenReturn(product);

        cartService.add(cart, 1, 5);
        cartService.add(cart, 1, 10);

        assertEquals(1, cart.getCartItems().size());
        assertEquals(15, cart.getCartItems().get(0).getQuantity());
    }
    @Test(expected = OutOfStockException.class)
    public void testAddProductOutOfStock() throws ProductNotFoundException, OutOfStockException {
        Cart cart = new Cart();

        Product product = new Product();
        product.setId(1L);
        product.setStock(10);

        when(productDao.getProduct(1L)).thenReturn(product);

        cartService.add(cart, 1, 4);
        cartService.add(cart, 1, 4);
        cartService.add(cart, 1, 4);
    }

/*
    @After
    public void clear() {
        cartService.setCart(new Cart());
    }

    @Test
    public void testAddingProduct() throws ProductNotFoundException, OutOfStockException {
        cartService.add(3, 5);
        assertEquals(1, cartService.getCart().getCartItems().size());
    }
*/


}