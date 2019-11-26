package com.rks.orderservice.converters;

import com.rks.orderservice.domain.Item;
import com.rks.orderservice.domain.Order;
import com.rks.orderservice.dto.request.OrderRequest;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderRequestToOrderConverter implements Converter<OrderRequest, Order> {

    public static final Logger log = LoggerFactory.getLogger(OrderRequestToOrderConverter.class);

    @Override
    public Order convert(MappingContext<OrderRequest, Order> context) {
        context.getDestination().setOrderDate(context.getSource().getOrderDate());

        for (Item i : context.getSource().getItems()) {
            context.getDestination().addItem(i.getName(), i.getQuantity(), i.getPrice());
        }
        return context.getDestination();
    }
}
