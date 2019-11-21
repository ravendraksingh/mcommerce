package com.rks.paymentservice.client.order.impl;

import com.rks.paymentservice.client.order.OrderClient;
import com.rks.paymentservice.dto.order.OrderResponse;
import com.rks.paymentservice.exceptions.BaseException;
import com.rks.paymentservice.exceptions.MicroServiceUnavailableException;
import com.rks.paymentservice.utility.RestMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import static com.rks.paymentservice.constants.Constant.ORDER_SERVICE;
import static com.rks.paymentservice.constants.ErrorCodeConstants.NULL_RESPONSE_RECEIVED;
import static com.rks.paymentservice.constants.ErrorCodeConstants.ORDER_SERVICE_UNAVAILABLE_ERROR_CODE;

@Component
public class OrderClientImpl implements OrderClient {

    private static final Logger logger = LogManager.getLogger(OrderClientImpl.class);

    @Value("${order-service-http-url}")
    private String orderServiceIp;

    @Override
    public OrderResponse getOrderDetails(Long orderId) {
        logger.info("Request received to get order details for orderId: {}", orderId);

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

            final String url = orderServiceIp + "order-service/api/v1/orders/" + orderId;

            logger.debug("url: {}, headers: {}, service: {}", url, httpHeaders, ORDER_SERVICE);

            OrderResponse response = RestMethod.restRequest(httpHeaders, OrderResponse.class, url, ORDER_SERVICE, HttpMethod.GET, null);
            //String response = RestMethod.restRequest(httpHeaders, String.class, url, ORDER_SERVICE, HttpMethod.GET, null);
            logger.debug("Response received from order service: {}", response);
            if (response == null) {
                BaseException ex = new BaseException("failure", NULL_RESPONSE_RECEIVED, "Null response");
                logger.error("Exception occurred. ResponseCode: {}. Message:{}.", ex.getCode(), ex.getMessage());
                throw ex;
            }
            return response;
        } catch (MicroServiceUnavailableException e) {
            e.setCode(ORDER_SERVICE_UNAVAILABLE_ERROR_CODE);
            logger.error(
                    "MicroServiceUnavailableException occurred: {} while fetching order details from Order service.  "
                            + "ResponseCode: {}.  Message: {}.", e.toString(),
                    e.getCode(), e.getCustomMessage());
            throw e;
        } catch (BaseException e) {
            logger.error("Exception occurred: {} while fetching order details from Order service.  "
                            + "ResponseCode: {}.  Message: {}.", e.toString(),
                    e.getCode(), e.getCustomMessage());
            throw e;
        }
        }
}
