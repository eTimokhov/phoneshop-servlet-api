package com.es.phoneshop.model.recentlyviewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public interface RecentlyViewedProductsService {

    void addProduct(HttpServletRequest request, Product product);
    void setMaxProductCount(int count);
    int getMaxProductCount();

}
