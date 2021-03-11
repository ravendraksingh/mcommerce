package com.rks.paymentservice.clients.orderservice;

import com.rks.paymentservice.dto.order.OrderResponse;

public interface OrderServiceClient {
    OrderResponse getOrderDetails(Long orderId);
    OrderResponse getOrderDetailsWithJwt(Long orderId);
    OrderResponse getOrderDetailsWithJwtToken(Long orderId, String jwtToken);
}
