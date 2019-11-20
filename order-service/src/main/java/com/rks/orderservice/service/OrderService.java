package com.rks.orderservice.service;

import com.rks.orderservice.domain.Order;
import com.rks.orderservice.dto.request.CreateOrderRequest;
import com.rks.orderservice.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    //OrderResponse createNewOrder(CreateOrderRequest newOrderRequest);
    OrderResponse createNewOrder(Order newOrderRequest);
    //Order createNewOrder(Order order);
    List<Order> findAllOrders();
    List<Order> findByOrderStatus(String status);
    OrderResponse findOrderById(Long orderId);
}
