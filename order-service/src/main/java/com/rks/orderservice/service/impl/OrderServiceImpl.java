package com.rks.orderservice.service.impl;

import com.rks.mcommon.exception.BaseException;
import com.rks.mcommon.exception.NotFoundException;
import com.rks.orderservice.domain.Order;
import com.rks.orderservice.dto.request.OrderRequest;
import com.rks.orderservice.dto.response.OrderResponse;
import com.rks.orderservice.mappers.OrderMapper;
import com.rks.orderservice.rabbitmq.OrderCreatedMessageProducer;
import com.rks.orderservice.rabbitmq.OrderMessage;
import com.rks.orderservice.repository.OrderRepository;
import com.rks.orderservice.service.OrderService;
import com.rks.orderservice.util.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.rks.mcommon.constants.CommonConstants.*;
import static com.rks.mcommon.constants.CommonErrorCodeConstants.DB_NOT_AVAILABLE_ERROR_CODE;
import static com.rks.mcommon.constants.CommonErrorCodeConstants.INTERNAL_SERVER_ERROR_CODE;
import static com.rks.orderservice.constants.Constant.INTERNAL_SERVER_ERROR_MSG;
import static com.rks.orderservice.constants.Constant.INVALID_ORDER_ID_MSG;
import static com.rks.orderservice.constants.ErrorCodeConstants.INVALID_ORDER_ID;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderRepository orderRepository;
    private OrderCreatedMessageProducer orderCreatedMessageProducer;
    private OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderCreatedMessageProducer orderCreatedMessageProducer, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderCreatedMessageProducer = orderCreatedMessageProducer;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponse createNewOrder(final OrderRequest orderRequest) {
        log.info("Going to create new order");
        try {
            Order request = new Order();
            orderMapper.map(orderRequest, request);
            request.setOrderStatus(StatusEnum.ORDER_CREATED.getStatus());

            Order savedOrder = orderRepository.save(request);
            log.debug("Order created successfully. OrderId: "+savedOrder.getId());
            //Sending data to RabbitMQ
            log.debug("Sending new order created message to queue. OrderId: " + savedOrder.getId());
            OrderMessage message = new OrderMessage(savedOrder.getId(), savedOrder.getOrderStatus());
            orderCreatedMessageProducer.sendMessage(message);
            return createOrderResponseForOrder(savedOrder);
        } catch (Exception e) {
            BaseException ex = new BaseException(FAILED, DB_NOT_AVAILABLE_ERROR_CODE, DB_NOT_AVAILABLE_ERROR_MSG);
            log.error("Exception occurred. Message {}. ResponseCode: {}. Message: {}", ex.getCode(), ex.getMessage());
            throw ex;
        }
    }

    private void sendMessageToOrderQueue(Order request) {
        OrderMessage message = new OrderMessage();
        message.setOrderId(request.getId());
        //message.setOrderDate(savedOrder.getOrderDate());
        message.setOrderStatus(request.getOrderStatus());
        orderCreatedMessageProducer.sendMessage(message);
    }

    @Override
    public List<Order> findAllOrders() {
        try {
            log.info("Fetching all orders");
            List<Order> orders = new ArrayList<>();
            orderRepository.findAll().forEach(orders::add);
            return orders;
        } catch (Exception e) {
            log.error("Exception occurred. Message {}.", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public OrderResponse findOrderById(Long orderId) {
        log.info("Fetching order details for order id {}.", orderId);
        try {
            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if (!optionalOrder.isPresent()) {
                BaseException exception = new NotFoundException(FAILED, INVALID_ORDER_ID, INVALID_ORDER_ID_MSG);
                log.error("Exception occurred while fetching the order for orderId {}. ResponseCode: {}. Message: {}",
                        orderId, exception.getCode(), exception.getMessage());
                throw exception;
            }
            Order fetchedOrder = optionalOrder.get();
            return createOrderResponseForOrder(fetchedOrder);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            BaseException ex = new BaseException(FAILED, INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR_MSG);
            log.error("Exception occurred while fetching the order for orderId {}. ResponseCode: {}. Message: {}",
                    orderId, ex.getCode(), ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void updateOrderStatus(Long orderId, String orderStatus) {
        log.info("Updating order status for orderId {}", orderId);
        try {
            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if (!optionalOrder.isPresent()) {
                BaseException exception = new NotFoundException(FAILED, INVALID_ORDER_ID, INVALID_ORDER_ID_MSG);
                log.error("Exception occurred while fetching the order for orderId {}. ResponseCode: {}. Message: {}",
                        orderId, exception.getCode(), exception.getMessage());
                throw exception;
            }

            Order fetchedOrder = optionalOrder.get();
            fetchedOrder.setOrderStatus(orderStatus);

            Order savedOrder = orderRepository.save(fetchedOrder);
            createOrderResponseForOrder(savedOrder);
        } catch (Exception e) {
            BaseException ex = new BaseException(FAILED, INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR);
            throw ex;
        }
    }

    private OrderResponse createOrderResponseForOrder(Order request) {
        OrderResponse response = new OrderResponse();
        orderMapper.map(request, response);
        return response;
    }

    @Override
    public List<Order> findByOrderStatus(String status) {
        try {
            log.info("Fetching all active orders.");
            String orderStatus = status.toUpperCase();
            List<Order> orderList = orderRepository.findByOrderStatus(orderStatus);
            return orderList;
        } catch (Exception e) {
            log.error("Exception occurred. Message: {}.", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteAllOrders() {
        try {
            log.debug("Deleting all orders.");
            orderRepository.deleteAll();
        } catch (Exception e) {
            log.error("Exception occurred. Message: {}.", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

}
