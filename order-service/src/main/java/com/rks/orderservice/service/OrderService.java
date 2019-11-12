package com.rks.orderservice.service;

import com.rks.orderservice.domain.Order;
import java.util.List;

public interface OrderService {

    Order createNewOrder(Order order);

    List<Order> findAllOrders();
}
