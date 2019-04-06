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

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {

    private static HttpSessionCartService cartService = HttpSessionCartService.getInstance();
    private static final String SESSION_CART_KEY = "sessionCart";

    @Mock
    private ProductDao productDao;
    @Mock
    private HttpServletRequest existingSessionRequest;
    @Mock
    private HttpSession existingSession;

    @Before
    public void setup() {
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
        product1.setPrice(BigDecimal.ONE);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setStock(30);
        product2.setPrice(BigDecimal.ONE);

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
        product.setPrice(BigDecimal.ONE);

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
        product.setPrice(BigDecimal.ONE);

        when(productDao.getProduct(1L)).thenReturn(product);

        cartService.add(cart, 1, 4);
        cartService.add(cart, 1, 4);
        cartService.add(cart, 1, 4);
    }

    @Test
    public void testUpdateExistingProduct() throws ProductNotFoundException, OutOfStockException {
        Cart cart = new Cart();

        Product product = new Product();
        product.setId(1L);
        product.setStock(30);
        product.setPrice(BigDecimal.ONE);

        when(productDao.getProduct(1L)).thenReturn(product);

        cartService.add(cart, 1, 5);
        cartService.update(cart,1, 10);

        assertEquals(10, cart.getCartItems().get(0).getQuantity());
    }

    @Test
    public void testUpdateNonexistentProduct() throws ProductNotFoundException, OutOfStockException {
        Cart cart = new Cart();

        Product product = new Product();
        product.setId(1L);
        product.setStock(30);
        product.setPrice(BigDecimal.ONE);

        when(productDao.getProduct(1L)).thenReturn(product);

        cartService.update(cart,1, 10);

        assertEquals(10, cart.getCartItems().get(0).getQuantity());
    }

    @Test
    public void testDeleteProduct() throws ProductNotFoundException, OutOfStockException {
        Cart cart = new Cart();

        Product product = new Product();
        product.setId(1L);
        product.setStock(30);
        product.setPrice(BigDecimal.ONE);

        when(productDao.getProduct(1L)).thenReturn(product);

        cartService.add(cart, 1, 5);
        cartService.delete(cart, 1);
        assertEquals(0, cart.getCartItems().size());
    }

    @Test
    public void testTotalPriceCalculation() throws ProductNotFoundException, OutOfStockException {
        Cart cart = new Cart();

        Product product1 = new Product();
        product1.setId(1L);
        product1.setStock(30);
        product1.setPrice(BigDecimal.valueOf(10));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setStock(30);
        product2.setPrice(BigDecimal.valueOf(50));

        when(productDao.getProduct(1L)).thenReturn(product1);
        when(productDao.getProduct(2L)).thenReturn(product2);

        cartService.add(cart, 1L, 1);
        assertEquals(BigDecimal.valueOf(10), cart.getTotalPrice());

        cartService.add(cart, 2L, 5);
        assertEquals(BigDecimal.valueOf(260), cart.getTotalPrice());
    }

    @Test
    public void testRecalculateEmptyCartTotalPrice() throws ProductNotFoundException, OutOfStockException {
        Cart cart = new Cart();

        Product product = new Product();
        product.setId(1L);
        product.setStock(30);
        product.setPrice(BigDecimal.ONE);

        when(productDao.getProduct(1L)).thenReturn(product);
        cartService.add(cart,1L, 1);
        cartService.delete(cart, 1L);

        assertEquals(BigDecimal.ZERO, cart.getTotalPrice());
    }
}