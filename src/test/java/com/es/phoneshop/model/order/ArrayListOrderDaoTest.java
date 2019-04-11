package com.es.phoneshop.model.order;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayListOrderDaoTest {

    private OrderDao orderDao;

    @Before
    public void setup() {
        orderDao = ArrayListOrderDao.getInstance();
        orderDao.getOrders().clear();
    }

    @Test
    public void testSaveOrder() {
        Order order = new Order();
        orderDao.save(order);
        assertEquals(1, orderDao.getOrders().size());
    }

    @Test
    public void testGetById() {
        Order order = new Order();
        orderDao.save(order);
        String secureId = order.getSecureId();
        assertNotNull(orderDao.getBySecureId(secureId));
    }

    @Test
    public void testGetByIdNoResult() {
        Order order = new Order();
        orderDao.save(order);
        assertNull(orderDao.getBySecureId("bcd234"));
    }

}