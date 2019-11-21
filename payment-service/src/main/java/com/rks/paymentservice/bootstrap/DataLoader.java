package com.rks.paymentservice.bootstrap;

import com.rks.paymentservice.client.order.OrderClient;
import com.rks.paymentservice.domain.PaymentMaster;
import com.rks.paymentservice.dto.order.OrderResponse;
import com.rks.paymentservice.repository.PaymentMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private PaymentMasterRepository paymentMasterRepository;

    @Autowired
    private OrderClient orderClient;

    public DataLoader(PaymentMasterRepository paymentMasterRepository) {
        this.paymentMasterRepository = paymentMasterRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //int count = orderService.findAllOrders().size();
        //if (count == 0) {
            //loadData();
        //}
        //getDataFromOrderService();
    }

    private void getDataFromOrderService() {
        OrderResponse response = orderClient.getOrderDetails(186L);
        log.info("Received response from order service");
        log.info(response.toString());
    }

    private void loadData() {
        log.info("Loading data ...........");
        PaymentMaster paymentMaster = new PaymentMaster();
        paymentMaster.setOrderDate(new Date());
        paymentMaster.setOrderId(183L);
        paymentMaster.setPaymentDate(new Date());
        paymentMaster.setPaymentStatus("paid");
        paymentMasterRepository.save(paymentMaster);
    }
}
