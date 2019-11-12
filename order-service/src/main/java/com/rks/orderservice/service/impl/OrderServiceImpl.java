package com.rks.orderservice.service.impl;

import com.rks.orderservice.domain.Order;
import com.rks.orderservice.repository.OrderRepository;
import com.rks.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createNewOrder(final Order orderRequest) {
        log.info("Going to create new order");

        Order savedOrder = orderRepository.save(orderRequest);
        log.info("Created new order. Order details are -> {} ", savedOrder.toString());
        return savedOrder;
    }

    @Override
    public List<Order> findAllOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);
        return orders;
    }
}
