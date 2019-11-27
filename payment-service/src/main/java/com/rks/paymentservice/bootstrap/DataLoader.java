package com.rks.paymentservice.bootstrap;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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

import static com.rks.paymentservice.constants.Constant.*;


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

        testJwtToken();
    }

    private void testJwtToken() throws Exception {
        String token = createJwtToken(269L);
        log.info("Token: {}", token);

        verifyJwtToken(token, 269L);
    }

    private void verifyJwtToken(String token, Long orderId) throws Exception {
        String secret = JWT_SECRET_KEY_ORDER_SERVICE;
        log.info("Verifying jwt token");

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("payment-service")
                .withClaim("orderId", String.valueOf(orderId))
                .withClaim(JWT_CLIENT_ID, PAYMENT_SERVICE)
                .build();

        jwtVerifier.verify(token);
        log.info("Jwt token verification successful");
        log.info("Extracting claims details");

        DecodedJWT jwt = JWT.decode(token);
        System.out.println(jwt.getIssuedAt());
        System.out.println(jwt.getExpiresAt());
        System.out.println(jwt.getClaim("Client-id").asString());
        System.out.println(jwt.getClaim("orderId").asString());
    }

    private String createJwtToken(Long orderId) throws Exception {
        String secret = JWT_SECRET_KEY_ORDER_SERVICE;
        log.info("Generating jwt token for orderId {}", orderId);

        //Date issuedAt = new Date(System.currentTimeMillis() - JWT_EXPIRATION_TIME * 2);
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + JWT_EXPIRATION_TIME);

        log.info("issuedAt: {}. expiresAt: {}", issuedAt, expiresAt);

        return JWT.create()
                .withIssuer("payment-service")
                .withClaim("orderId", String.valueOf(orderId))
                .withClaim(JWT_CLIENT_ID, PAYMENT_SERVICE)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(secret));
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
