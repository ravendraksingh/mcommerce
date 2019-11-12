package com.rks.orderservice.bootstrap;

import com.rks.orderservice.domain.Item;
import com.rks.orderservice.domain.Order;
import com.rks.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private final OrderService orderService;

    public DataLoader(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = orderService.findAllOrders().size();
        //if (count == 0) {
            loadData();
        //}
    }

    private void loadData() {
        log.info("Loading data ...........");
        Order myOrder1 = new Order();

        Item item1 = new Item();
        item1.setName("Trouser");
        item1.setQuantity(5);
        item1.setPrice(new BigDecimal(9374));
        item1.setOrder(myOrder1);

        myOrder1.getItems().add(item1);
        orderService.createNewOrder(myOrder1);

        Order myOrder2 = new Order();
        Item item2 = new Item();
        item2.setName("Saree");
        item2.setQuantity(8);
        item2.setPrice(new BigDecimal(12309));
        item2.setOrder(myOrder2);

        myOrder2.getItems().add(item2);
        orderService.createNewOrder(myOrder2);
    }
}
