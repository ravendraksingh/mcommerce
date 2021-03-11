package com.rks.orderservice.bootstrap;

import com.rks.orderservice.converters.NotInUse_OrderConverter;
import com.rks.orderservice.domain.Item;
import com.rks.orderservice.domain.Order;
import com.rks.orderservice.dto.response.OrderResponse;
import com.rks.orderservice.repository.OrderRepository;
import com.rks.orderservice.service.OrderService;
import com.rks.orderservice.util.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;


@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    public DataLoader(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = orderService.findAllOrders().size();
//        if (count == 0) {
//            loadData();
//        }
        //testUpdateOrder();
    }

    private void testUpdateOrder() {
        log.info("testing update order service");
        OrderResponse orderResponse = orderService.findOrderById(186L);
        //ModelMapper mapper = new ModelMapper();
        //System.out.println("orderResponse: " + orderResponse);
        //Order o1 = mapper.map(orderResponse, Order.class);
        //System.out.println("o1: " + o1);

        Order o2 = NotInUse_OrderConverter.orderResponseToOrder(orderResponse);
        //
        o2.setOrderDate(new Date());
        //o2.setOrderStatus(StatusEnum.ORDER_PAID.getStatus());
        //orderRepository.save(o2);
        o2.setOrderStatus(StatusEnum.ORDER_DELIVERED.getStatus());
        //orderService.updateOrder(o2);
        System.out.println(o2);
    }

    private void loadData() {
        log.info("Loading data ...........");

       Order myOrder = new Order();
        myOrder.setOrderStatus(StatusEnum.ORDER_CREATED.getStatus());
        myOrder.setOrderDate(new Date());

        Item myItem = new Item();
        myItem.setName("Trouser");
        myItem.setQuantity(5);
        myItem.setPrice(new BigDecimal(9374));
        myItem.setOrder(myOrder);
        myOrder.getItems().add(myItem);

        Item myItem2 = new Item();
        myItem2.setName("Clock");
        myItem2.setQuantity(1);
        myItem2.setPrice(new BigDecimal(384));
        myItem2.setOrder(myOrder);
        myOrder.getItems().add(myItem2);

        //orderService.createNewOrder(myOrder);
    }
}
