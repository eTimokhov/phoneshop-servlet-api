package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class OrderServiceImplTest {

    private OrderService orderService;
    private CartService cartService;

    @Before
    public void setup() {
        orderService = OrderServiceImpl.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Test
    public void testCreateOrderCorrectDeliveryMode() {
        Order order = orderService.createOrder(new Cart(), DeliveryMode.STORE_PICKUP);
        assertEquals(DeliveryMode.STORE_PICKUP, order.getDeliveryMode());

        order = orderService.createOrder(new Cart(), DeliveryMode.COURIER);
        assertEquals(DeliveryMode.COURIER, order.getDeliveryMode());
    }

    @Test
    public void testCreateOrderPriceCalculation() {
        Cart cart = new Cart();
        cart.setTotalPrice(BigDecimal.valueOf(100));
        Order order = orderService.createOrder(cart, DeliveryMode.COURIER);

        assertEquals(BigDecimal.valueOf(100), order.getSubTotalPrice());
        assertEquals(BigDecimal.valueOf(110), order.getTotalPrice());
    }


}