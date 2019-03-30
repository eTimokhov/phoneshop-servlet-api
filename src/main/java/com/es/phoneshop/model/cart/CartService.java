package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ProductNotFoundException;

public interface CartService {

    Cart getCart();
    void add(long productId, int quantity) throws ProductNotFoundException, OutOfStockException;

}
