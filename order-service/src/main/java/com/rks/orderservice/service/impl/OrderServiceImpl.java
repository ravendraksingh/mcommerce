package com.rks.orderservice.service.impl;

import com.rks.orderservice.domain.Item;
import com.rks.orderservice.domain.Order;
import com.rks.orderservice.dto.request.CreateOrderRequest;
import com.rks.orderservice.dto.response.OrderItem;
import com.rks.orderservice.dto.response.OrderResponse;
import com.rks.orderservice.rabbitmq.AMQPMessageProducer;
import com.rks.orderservice.rabbitmq.OrderCreatedMessageProducer;
import com.rks.orderservice.rabbitmq.OrderMessage;
import com.rks.orderservice.repository.OrderRepository;
import com.rks.orderservice.service.OrderService;
import com.rks.orderservice.util.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AMQPMessageProducer producer;

    @Autowired
    private OrderCreatedMessageProducer orderCreatedMessageProducer;

    @Override
    public OrderResponse createNewOrder(final Order newOrderRequest) {
        log.info("Going to create new order");
        try {
            Order request = newOrderRequest;
            request.setOrderStatus(StatusEnum.ORDER_CREATED.getStatus());
            request.getItems().forEach(item -> item.setOrder(request));

            Order savedOrder = orderRepository.save(request);
            log.info("Order created successfully.");

            //Sending data to RabbitMQ
            log.info("Going to send message to RabbitMQ");

            OrderMessage message = new OrderMessage();
            message.setOrderId(savedOrder.getId());
            //message.setOrderDate(savedOrder.getOrderDate());
            message.setOrderStatus(savedOrder.getOrderStatus());
            producer.sendMessage(message);
            orderCreatedMessageProducer.sendMessage(message);

            return createOrderResponseForOrder(savedOrder);

        } catch (Exception e) {
            log.error("Exception occurred. Message {}.", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /*private Order createOrderForOrderRequest(CreateOrderRequest request) {
        log.info("Creating order from order request");
        Order order = new Order();
        order.setOrderStatus(StatusEnum.ORDER_CREATED.getStatus());
        order.setOrderDate(new Date());

        List<Item> items = new ArrayList<>();
        log.info("Fetching item list from order request");

        for(OrderItem orderItem : request.getItems()) {
            Item item = new Item();
            item.setName(orderItem.getName());
            item.setQuantity(orderItem.getQuantity());
            item.setPrice(orderItem.getPrice());
            item.setOrder(order);
            log.info("Item {}.", item);
            items.add(item);
        }
        order.setItems(items);
        log.info("New order object -> {}", order.toString());
        return order;
    }*/

    private OrderResponse createOrderResponseForOrder(Order savedOrder) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(savedOrder.getId());
        response.setOrderStatus(savedOrder.getOrderStatus());
        BigDecimal orderAmount = BigDecimal.ZERO;

        List<OrderItem> items = new ArrayList<>();

        for (Item item : savedOrder.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(item.getId());
            orderItem.setName(item.getName());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());

            orderAmount = orderAmount.add(item.getPrice());

            items.add(orderItem);
        }
        response.setOrderAmount(orderAmount);
        response.setItemList(items);
        //response.setCode(201);
        //response.setStatus(StatusEnum.SUCCESS.getStatus());
        //response.setMessage("Order created successfully with order id "+response.getOrderId());
        return response;
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

    /*@Override
    public OrderResponse findOrderById(Long orderId) {
        log.info("Fetching order details for order id {}.", orderId);

        OrderResponse response = new OrderResponse();
        response.setOrderId(orderId);

        Order fetchedOrder;
        fetchedOrder = orderRepository.findById(orderId).get();

        if (fetchedOrder != null) {
            for (Item item : fetchedOrder.getItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(item.getId());
                orderItem.setName(item.getName());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setPrice(item.getPrice());

                response.getItemList().add(orderItem);
            }
        }
        //response.setCode(200);
        //response.setStatus(StatusEnum.SUCCESS.getStatus());
        return response;
    }*/

    @Override
    public OrderResponse findOrderById(Long orderId) {
        log.info("Fetching order details for order id {}.", orderId);

        Order fetchedOrder;
        fetchedOrder = orderRepository.findById(orderId).get();

        if (fetchedOrder == null) {
            return new OrderResponse();
        } else {
            return createOrderResponseForOrder(fetchedOrder);
        }
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

    /*
    @Override
    public List<FDMasterData> getActiveFdsForUser(String userId) {
        try {
            logger.info("Fetching active FDs for UserId: {}.", userId);

            List<FDMasterData> fdMasterDataList = fdMasterRepository
                    .findByUserIdAndStatus(userId, "active", new Sort(
                            Direction.DESC, "bookingDate"));
            return fdMasterDataList;
        } catch (Exception e) {
            BaseException ex = ServiceErrorFactory
                    .getException(TSERVICE, String.valueOf(DB_SERVICE_UNAVAILABLE_MESSAGE)).get();
            logger.error(
                    "Exception occurred: {} while fetching data for ResponseCode: {}. Message: {}.",
                    CommonUtils.exceptionFormatter(e), ex.getCode(), ex.getMessage());
            throw ex;
        }
    }
     */
}
