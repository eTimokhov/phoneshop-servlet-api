package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MiniCartServletTest {

    @Mock
    private CartService cartService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    private Cart cart = new Cart();
    private MiniCartServlet servlet = new MiniCartServlet();

    @Before
    public void setup() {
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.setCartService(cartService);
    }

    @Test
    public void testDoGet() throws IOException, ServletException {
        servlet.doGet(request, response);
        verify(requestDispatcher).include(request, response);
    }

}