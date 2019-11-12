package com.rks.orderservice.controller;

import com.rks.orderservice.domain.Order;
import com.rks.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(path = "/orders", method = RequestMethod.POST)
    public Order createOrder(@RequestBody Order orderRequest) {
        return orderService.createNewOrder(orderRequest);
    }
}
