package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ProductNotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface CartService {

    Cart getCart(HttpServletRequest request);
    void add(Cart cart, long productId, int quantity) throws ProductNotFoundException, OutOfStockException;

}
