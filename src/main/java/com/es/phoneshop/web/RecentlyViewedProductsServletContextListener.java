package com.es.phoneshop.web;

import com.es.phoneshop.model.recentlyviewed.HttpSessionRecentlyViewedProductsService;
import com.es.phoneshop.model.recentlyviewed.RecentlyViewedProductsService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class RecentlyViewedProductsServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String countParam = sce.getServletContext().getInitParameter("recentlyViewedProductCount");
        int count = Integer.parseInt(countParam);
        RecentlyViewedProductsService service = HttpSessionRecentlyViewedProductsService.getInstance();
        service.setMaxProductCount(count);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
