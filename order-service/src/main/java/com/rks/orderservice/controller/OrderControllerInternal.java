package com.rks.orderservice.controller;

import com.rks.orderservice.domain.Order;
import com.rks.orderservice.dto.request.OrderRequest;
import com.rks.orderservice.dto.response.ErrorResponse;
import com.rks.orderservice.dto.response.OrderResponse;
import com.rks.orderservice.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-service/int")
public class OrderControllerInternal {
    public static final Logger log = LoggerFactory.getLogger(OrderControllerInternal.class);

    private OrderService orderService;

    @Autowired
    public OrderControllerInternal(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(
            httpMethod = "GET",
            value = "Get All Orders"
    )
    @GetMapping("/v1/orders")
    public List<Order> getAllOrders() {
        return orderService.findAllOrders();
    }

    @ApiOperation(
            httpMethod = "GET",
            value = "Get Order Details",
            response = OrderResponse.class,
            notes = ""
    )
    @RequestMapping(
            value = "/v1/orders/{orderId}",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 400,
                    message = "Bad Request",
                    response = ErrorResponse.class
            ),
            @ApiResponse(
                    code = 401,
                    message = "Unauthorized",
                    response = ErrorResponse.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "Internal Service Error",
                    response = ErrorResponse.class
            )
    })
    public OrderResponse getOrder(@PathVariable(value="orderId") Long orderId) {
        return orderService.findOrderById(orderId);
    }


    @ApiOperation(
            httpMethod = "POST",
            value = "Create New Order",
            response = OrderResponse.class,
            notes = ""
    )
    @RequestMapping(
            value = "/v1/orders",
            method = RequestMethod.POST,
            produces = "application/json"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 400,
                    message = "Bad Request",
                    response = ErrorResponse.class
            ),
            @ApiResponse(
                    code = 401,
                    message = "Unauthorized",
                    response = ErrorResponse.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "Internal Service Error",
                    response = ErrorResponse.class
            )
    })
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createNewOrder(orderRequest);
    }

    @ApiOperation(
            httpMethod = "DELETE",
            value = "Delete All Orders"
    )
    @DeleteMapping("/v1/orders")
    public void deleteAllOrders() {
        orderService.deleteAllOrders();
    }
}
