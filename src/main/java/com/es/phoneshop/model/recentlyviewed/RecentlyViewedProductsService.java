package com.es.phoneshop.model.recentlyviewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

public interface RecentlyViewedProductsService {

    void addProduct(LinkedList<Product> products, Product product);
    LinkedList<Product> getProducts(HttpServletRequest request);
    void setMaxProductCount(int count);
    int getMaxProductCount();

}
