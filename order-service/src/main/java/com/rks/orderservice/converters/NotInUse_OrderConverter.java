package com.rks.orderservice.converters;

import com.rks.orderservice.domain.Item;
import com.rks.orderservice.domain.Order;
import com.rks.orderservice.dto.response.OrderItem;
import com.rks.orderservice.dto.response.OrderResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@Component
public class NotInUse_OrderConverter {

    public static Order orderResponseToOrder(OrderResponse orderResponse) {
        Order result = new Order();
        result.setId(orderResponse.getOrderId());
        result.setOrderDate(orderResponse.getOrderDate());
        result.setOrderStatus(orderResponse.getOrderStatus());

        for (OrderItem i : orderResponse.getItems()) {
            Item item = new Item();
            item.setName(i.getName());
            item.setPrice(i.getPrice());
            item.setQuantity(i.getQuantity());
            result.getItems().add(item);
        }

        result.getItems().forEach(item -> item.setOrder(result));
        return result;
    }

    public static OrderResponse orderToOrderResponse(Order order) {
        OrderResponse result = new OrderResponse();
        result.setOrderId(order.getId());
        result.setOrderDate(order.getOrderDate());
        result.setOrderStatus(order.getOrderStatus());

        BigDecimal orderAmount = BigDecimal.ZERO;
        List<OrderItem> items = new ArrayList<>();

        for (Item i : order.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setName(i.getName());
            orderItem.setQuantity(i.getQuantity());
            orderItem.setPrice(i.getPrice());
            orderItem.setPrice(i.getPrice());

            orderAmount = orderAmount.add(i.getPrice());
            items.add(orderItem);
        }
        result.setOrderAmount(orderAmount);
        result.setItems(items);
        return result;

    }

}
