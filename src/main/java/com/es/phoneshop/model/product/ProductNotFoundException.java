package com.es.phoneshop.model.product;

import javax.servlet.ServletException;

public class ProductNotFoundException extends ServletException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
