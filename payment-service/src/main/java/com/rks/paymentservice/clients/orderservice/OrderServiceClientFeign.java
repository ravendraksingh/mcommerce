package com.rks.paymentservice.clients.orderservice;

import com.rks.paymentservice.dto.order.OrderResponse;
import com.rks.paymentservice.dto.order.UpdateOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", url = "localhost:8200")
public interface OrderServiceClientFeign {
    @PutMapping("/order-service/int/v1/orders/{orderId}")
    OrderResponse updateOrder(@PathVariable("orderId") Long orderId, @RequestBody UpdateOrderRequest request);
}
