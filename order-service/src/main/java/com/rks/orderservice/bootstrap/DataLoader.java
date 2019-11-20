package com.rks.orderservice.bootstrap;

import com.rks.orderservice.domain.Item;
import com.rks.orderservice.domain.Order;
import com.rks.orderservice.dto.request.CreateOrderRequest;
import com.rks.orderservice.dto.response.OrderItem;
import com.rks.orderservice.repository.OrderRepository;
import com.rks.orderservice.service.OrderService;
import com.rks.orderservice.util.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    private OrderService orderService;

    public DataLoader(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = orderService.findAllOrders().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        log.info("Loading data ...........");

       /* Order myOrder = new Order();
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

        orderService.createNewOrder(myOrder);*/
    }
}
