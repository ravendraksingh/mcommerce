package com.rks.orderservice.controller;

import com.rks.orderservice.domain.Order;
import com.rks.orderservice.dto.request.OrderRequest;
import com.rks.orderservice.dto.response.OrderResponse;
import com.rks.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-service/api/v1")
public class OrderController {

    public static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.findAllOrders();
    }

    @GetMapping("/orders/{orderId}")
    public OrderResponse getOrder(@PathVariable(value="orderId") Long orderId) {
        return orderService.findOrderById(orderId);
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createNewOrder(orderRequest);
    }

    //public void deleteOrder(Long orderId) ;

    /*@RequestMapping(value = "/orders/update-status?orderId={orderId}&newStatus={newStatus}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrderStatus(@RequestParam("orderId") Long orderId,
                                           @RequestParam("newStatus") String newStatus) {
        orderService.updateOrderStatus(orderId, newStatus);
    }*/


    /*@GetMapping("/orders/{orderStatus}")
    public List<Order> getAllActiveOrders(@PathVariable("orderStatus") String orderStatus) {
        return orderService.findByOrderStatus(orderStatus);
    }*/
}
