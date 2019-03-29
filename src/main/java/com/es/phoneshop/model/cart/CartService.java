package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ProductNotFoundException;

public interface CartService {

    void add(long productId, int quantity) throws ProductNotFoundException;

}
