package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.ProductNotFoundException;

public class HttpSessionCartService implements CartService {

    private static HttpSessionCartService instance;
    private ProductDao productDao;
    private Cart cart = new Cart();

    private HttpSessionCartService(){
        productDao = ArrayListProductDao.getInstance();
    }

    public static HttpSessionCartService getInstance() {
        if (instance == null) {
            synchronized (HttpSessionCartService.class) {
                if (instance == null) {
                    instance = new HttpSessionCartService();
                }
            }
        }
        return instance;
    }

    @Override
    public void add(long productId, int quantity) throws ProductNotFoundException {
        Product product = productDao.getProduct(productId);
        CartItem cartItem = new CartItem(product, quantity);
        cart.getCartItems().add(cartItem);
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
