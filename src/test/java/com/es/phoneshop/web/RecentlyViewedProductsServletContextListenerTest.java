package com.es.phoneshop.web;

import com.es.phoneshop.model.recentlyviewed.HttpSessionRecentlyViewedProductsService;
import com.es.phoneshop.model.recentlyviewed.RecentlyViewedProductsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)

public class RecentlyViewedProductsServletContextListenerTest {

    private RecentlyViewedProductsServletContextListener listener = new RecentlyViewedProductsServletContextListener();

    @Mock
    private ServletContextEvent servletContextEvent;
    @Mock
    private ServletContext servletContext;
    @Mock
    private RecentlyViewedProductsService service;

    @Before
    public void setup() {
        service = HttpSessionRecentlyViewedProductsService.getInstance();
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.getInitParameter("recentlyViewedProductCount")).thenReturn("5");
    }

    @Test
    public void testSetMaxProductCount() {
        listener.contextInitialized(servletContextEvent);
        assertEquals(5, service.getMaxProductCount());
    }

}