package com.rks.paymentservice.service.impl;

import com.rks.paymentservice.client.order.OrderClient;
import com.rks.paymentservice.domain.PaymentMaster;
import com.rks.paymentservice.dto.order.OrderResponse;
import com.rks.paymentservice.dto.response.PaymentMasterResponse;
import com.rks.paymentservice.exceptions.BadRequestException;
import com.rks.paymentservice.exceptions.BaseException;
import com.rks.paymentservice.exceptions.NotFoundException;
import com.rks.paymentservice.repository.PaymentMasterRepository;
import com.rks.paymentservice.service.IPaymentService;
import com.rks.paymentservice.util.PaymentStatus;
import com.rks.paymentservice.util.StatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.rks.paymentservice.constants.ErrorCodeConstants.INVALID_PAYMENT_ID;
import static com.rks.paymentservice.constants.ErrorCodeConstants.ORDER_DETAILS_COULD_NOT_BE_FETCHED;


@Service
public class PaymentServiceImpl implements IPaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private PaymentMasterRepository paymentMasterRepository;

    @Autowired
    private OrderClient orderClient;

    public PaymentServiceImpl(PaymentMasterRepository paymentMasterRepository) {
        this.paymentMasterRepository = paymentMasterRepository;
    }

    @Override
    public PaymentMasterResponse getPaymentMasterDataByPaymentId(Long paymentId) {
        log.info("Fetching payment master details for paymentId {}.", paymentId);

        if(StringUtils.isBlank(String.valueOf(paymentId))) {
                throw new BadRequestException("success", 200, "Payment Id cannot be empty");
        }

        Optional<PaymentMaster> data = paymentMasterRepository.findById(paymentId);
        if (!data.isPresent()) {
            throw new NotFoundException("failure", INVALID_PAYMENT_ID, "invalid payment id");
        }
        log.info("Fetched payment master data for payment id {}.", paymentId);
        return createPaymentMasterResponseForPayment(data.get());
    }

    private PaymentMasterResponse createPaymentMasterResponseForPayment(PaymentMaster data) {
        PaymentMasterResponse response = new PaymentMasterResponse();
        response.setPaymentId(data.getId());
        response.setPaymentDate(data.getPaymentDate());
        response.setOrderId(data.getOrderId());
        response.setOrderDate(data.getOrderDate());
        response.setPaymentStatus(data.getPaymentStatus());
        return response;
    }

    @Override
    public PaymentMasterResponse createPayment(PaymentMaster request) {
        try {
            log.info("Going to create new payment.");
            PaymentMaster savedData = paymentMasterRepository.save(request);
            return createPaymentMasterResponseForPayment(savedData);
        } catch (BaseException e) {
            BaseException ex = new BaseException(PaymentStatus.PAYMENT_FAILED.getStatus(),1102,"failed payment");
            log.error("Exception occurred during payment creation for payment id {}. Message: {}", ex.getCode(),
                    ex.getMessage());
            throw ex;
        }
 }

    @Override
    public PaymentMasterResponse createPaymentForNewOrder(Long orderId) {
        try {
            log.info("Fetching order details from order-service for orderId {}.", orderId);
            OrderResponse orderResponse = orderClient.getOrderDetails(orderId);

            log.info("Order details fetched. orderResponse {}.", orderResponse.toString());
            log.info("Fetching order details from orderResponse");

            PaymentMaster request = createPaymentRequestForOrder(orderResponse);
            PaymentMaster data = paymentMasterRepository.save(request);
            return createPaymentMasterResponseForPayment(data);
        } catch (Exception e) {
            throw e;
        }
    }

    private PaymentMaster createPaymentRequestForOrder(OrderResponse orderResponse) {
        log.info("orderIdFromResponse = {} and orderAmount = {}", orderResponse.getOrder_id(), orderResponse.getOrder_amount());
        PaymentMaster requestData = new PaymentMaster();
        requestData.setOrderId(orderResponse.getOrder_id());
        requestData.setPaymentDate(new Date());
        requestData.setOrderDate(new Date());
        requestData.setPaymentStatus(PaymentStatus.SUCCESS.getStatus());
        return requestData;
    }

    @Override
    public OrderResponse getOrderDetailsRemote(Long orderId) {
        log.info("Going to fetch order details from order service via network call");
        OrderResponse orderResponse = orderClient.getOrderDetails(orderId);
        log.info("Response received. orderResponse: {}", orderResponse);
        return orderResponse;
    }

    @Override
    public OrderResponse getOrderDetailsRemoteWithJwt(Long orderId) {
        log.info("Going to fetch order details from order service via network call");
        OrderResponse orderResponse = orderClient.getOrderDetailsWithJwt(orderId);
        log.info("Response received. orderResponse: {}", orderResponse);
        return orderResponse;
    }

    @Override
    public OrderResponse getOrderDetailsRemoteWithJwtToken(Long orderId, String jwtToken) {
        log.info("Going to fetch order details from order service via network call");
        OrderResponse orderResponse = orderClient.getOrderDetailsWithJwtToken(orderId, jwtToken);
        log.info("Response received. orderResponse: {}", orderResponse);
        return orderResponse;
    }
}
