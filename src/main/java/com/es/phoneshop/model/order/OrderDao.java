package com.es.phoneshop.model.order;

import java.util.List;

public interface OrderDao {
    void save(Order order);
    Order getBySecureId(String secureId);
    List<Order> getOrders();
}
