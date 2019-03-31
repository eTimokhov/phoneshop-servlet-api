package com.es.phoneshop.model.recentlyviewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


public class HttpSessionRecentlyViewedProductsService implements RecentlyViewedProductsService {

    private static HttpSessionRecentlyViewedProductsService instance;
    private int maxProductCount;
    private static final String SESSION_RECENTLY_VIEWED_KEY = "recentlyViewedProducts";

    public static HttpSessionRecentlyViewedProductsService getInstance() {
        if (instance == null) {
            synchronized (HttpSessionRecentlyViewedProductsService.class) {
                if (instance == null) {
                    instance = new HttpSessionRecentlyViewedProductsService();
                }
            }
        }
        return instance;
    }

    private HttpSessionRecentlyViewedProductsService() {
    }

    @Override
    public void addProduct(LinkedList<Product> products, Product product) {
        if (products.contains(product)) {
            products.remove(product);
        } else if (products.size() == maxProductCount) {
            products.removeLast();
        }
        products.addFirst(product);
    }

    @Override
    public LinkedList<Product> getProducts(HttpServletRequest request) {
        HttpSession session = request.getSession();
        LinkedList<Product> products = (LinkedList<Product>) session.getAttribute(SESSION_RECENTLY_VIEWED_KEY);
        if (products == null) {
            products = new LinkedList<>();
            session.setAttribute(SESSION_RECENTLY_VIEWED_KEY, products);
        }
        return products;
    }

    @Override
    public int getMaxProductCount() {
        return maxProductCount;
    }

    @Override
    public void setMaxProductCount(int productCount) {
        this.maxProductCount = productCount;
    }

}
