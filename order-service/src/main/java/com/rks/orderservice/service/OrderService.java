package com.rks.orderservice.service;

import com.rks.orderservice.domain.Order;
import com.rks.orderservice.dto.request.OrderRequest;
import com.rks.orderservice.dto.request.UpdateOrderRequest;
import com.rks.orderservice.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    List<Order> findAllOrders();
    List<Order> findByOrderStatus(String status);
    OrderResponse findOrderById(Long orderId);
    void updateOrderStatus(Long orderId, String orderStatus);
    OrderResponse createNewOrder(OrderRequest orderRequest);
    void deleteAllOrders();
    OrderResponse updateOrder(Long orderId, UpdateOrderRequest request);
}
