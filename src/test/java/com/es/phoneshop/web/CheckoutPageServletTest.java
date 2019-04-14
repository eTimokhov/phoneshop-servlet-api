package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {

    private static final String SESSION_CART_KEY = "sessionCart";

    private static CheckoutPageServlet servlet = new CheckoutPageServlet();

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;

    private Cart cart = new Cart();

    @BeforeClass
    public static void init() {
        servlet.init();
    }

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(SESSION_CART_KEY)).thenReturn(cart);
        when(request.getContextPath()).thenReturn("/phoneshop-servlet-api");
        when(request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getParameter("deliveryMode")).thenReturn(null);
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getParameter("firstName")).thenReturn("John");
        when(request.getParameter("lastName")).thenReturn("Doe");
        when(request.getParameter("phoneNumber")).thenReturn("+1-541-123-4567");
        when(request.getParameter("deliveryMode")).thenReturn("COURIER");
        when(request.getParameter("deliveryDate")).thenReturn("2019-04-11");
        when(request.getParameter("address")).thenReturn("123 Main St, Anytown, USA");
        when(request.getParameter("paymentMethod")).thenReturn("Credit card");

        servlet.doPost(request, response);
        verify(response).sendRedirect(startsWith(request.getContextPath() + "/order/overview/"));
    }

    @Test
    public void testDoPostWithError() throws ServletException, IOException {
        when(request.getParameter("firstName")).thenReturn("John");
        when(request.getParameter("lastName")).thenReturn("Doe");
        when(request.getParameter("phoneNumber")).thenReturn("+1-541-123-4567");
        when(request.getParameter("deliveryMode")).thenReturn("COURIER");
        when(request.getParameter("deliveryDate")).thenReturn("unparsable date");
        when(request.getParameter("address")).thenReturn("123 Main St, Anytown, USA");
        when(request.getParameter("paymentMethod")).thenReturn("Credit card");

        servlet.doPost(request, response);
        verify(requestDispatcher).forward(request, response);
    }
}