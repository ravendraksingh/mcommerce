package com.rks.orderservice.converters;

import com.rks.orderservice.domain.Item;
import com.rks.orderservice.domain.Order;
import com.rks.orderservice.dto.response.OrderResponse;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class OrderToOrderResponseConverter implements Converter<Order, OrderResponse> {
    @Override
    public OrderResponse convert(MappingContext<Order, OrderResponse> context) {
        context.getDestination().setOrderId(context.getSource().getId());
        context.getDestination().setOrderDate(context.getSource().getOrderDate());
        context.getDestination().setOrderStatus(context.getSource().getOrderStatus());
        context.getDestination().setPaymentStatus(context.getSource().getPaymentStatus());

        if (context.getSource().getItems() != null) {
            for (Item i : context.getSource().getItems()) {
                context.getDestination().addItem(i.getId(), i.getName(), i.getQuantity(), i.getPrice());
                context.getDestination().updateOrderAmount(i.getPrice());
            }
        }
        return context.getDestination();
    }
}
