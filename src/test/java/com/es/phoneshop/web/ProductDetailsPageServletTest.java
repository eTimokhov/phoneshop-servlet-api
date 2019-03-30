package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;
import com.es.phoneshop.model.recentlyviewed.RecentlyViewedProductsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;
    @Mock
    private CartService cartService;
    @Mock
    private RecentlyViewedProductsService recentlyViewedProductsService;

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();
    private Cart cart;
    private Product product;
    private LinkedList<Product> recentlyViewedProducts;

    @Before
    public void setup() throws ProductNotFoundException {
        product = new Product();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn("/2");
        when(productDao.getProduct(anyLong())).thenReturn(product);

        cart = new Cart();
        when(cartService.getCart(request)).thenReturn(cart);

        recentlyViewedProducts = new LinkedList<>();
        when(recentlyViewedProductsService.getProducts(request)).thenReturn(recentlyViewedProducts);

        servlet.setProductDao(productDao);
        servlet.setCartService(cartService);
        servlet.setRecentlyViewedProductsService(recentlyViewedProductsService);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testSetProduct() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute("product", product);
    }

    @Test
    public void testDoPostQuantityIsNotANumber() throws ServletException, IOException {
        when(request.getParameter("quantity")).thenReturn("str");
        servlet.doPost(request, response);
        verify(request).setAttribute("error", "Not a number");
    }

    @Test
    public void testDoPostQuantityIsNotPositive() throws ServletException, IOException {
        when(request.getParameter("quantity")).thenReturn("-2");
        servlet.doPost(request, response);
        verify(request).setAttribute("error", "Invalid value");
    }

    @Test
    public void testDoPostSendRedirect() throws ServletException, IOException {
        when(request.getParameter("quantity")).thenReturn("1");
        servlet.doPost(request, response);
        verify(response).sendRedirect(any());
    }
}