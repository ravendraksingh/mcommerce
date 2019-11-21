package com.rks.paymentservice.client.order;

import com.rks.paymentservice.dto.order.OrderResponse;

public interface OrderClient {
    OrderResponse getOrderDetails(Long orderId);
}
