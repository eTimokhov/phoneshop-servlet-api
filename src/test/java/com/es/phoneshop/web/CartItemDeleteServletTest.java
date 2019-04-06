package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartItemDeleteServletTest {

    @Mock
    private CartService cartService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    private Cart cart = new Cart();
    private CartItemDeleteServlet servlet = new CartItemDeleteServlet();

    @Before
    public void setup() {
        when(cartService.getCart(request)).thenReturn(cart);
        when(request.getPathInfo()).thenReturn("/1");
        servlet.setCartService(cartService);
    }

    @Test
    public void testDoPost() throws IOException {
        servlet.doPost(request, response);
        verify(response).sendRedirect(request.getContextPath() + "/cart?message=Deleted successfully");
    }
}