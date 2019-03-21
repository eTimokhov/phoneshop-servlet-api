package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductDao productDao;

    private ProductListPageServlet servlet = new ProductListPageServlet();
    private List<Product> products;

    @Before
    public void setup(){
        String query = "iphone 6";
        String sortBy = "price";

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter("query")).thenReturn(query);
        when(request.getParameter("sortBy")).thenReturn(sortBy);
        when(productDao.findProducts(eq(query), eq(sortBy), anyBoolean())).thenReturn(products);
        servlet.setProductDao(productDao);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testSetProductList() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request).setAttribute("products", products);
    }
}