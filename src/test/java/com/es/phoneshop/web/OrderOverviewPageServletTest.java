package com.es.phoneshop.web;

import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDao;
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

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class OrderOverviewPageServletTest {

    private OrderOverviewPageServlet servlet = new OrderOverviewPageServlet();

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private OrderDao orderDao;
    @Mock
    private RequestDispatcher requestDispatcher;

    private Order order = new Order();

    @Before
    public void setup() {
        servlet.setOrderDao(orderDao);
        when(request.getPathInfo()).thenReturn("/abc123");
        when(request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(orderDao.getBySecureId("abc123")).thenReturn(order);
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetWithError() throws IOException, ServletException {
        when(orderDao.getBySecureId("abc123")).thenReturn(null);
        servlet.doGet(request, response);
        verify(response).sendError(eq(HttpServletResponse.SC_NOT_FOUND), anyString());
    }

}