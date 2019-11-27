package com.rks.paymentservice.client.order.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rks.paymentservice.client.order.OrderClient;
import com.rks.paymentservice.constants.Constant;
import com.rks.paymentservice.dto.order.OrderResponse;
import com.rks.paymentservice.exceptions.BaseException;
import com.rks.paymentservice.exceptions.MicroServiceUnavailableException;
import com.rks.paymentservice.util.StatusEnum;
import com.rks.paymentservice.utility.CommonUtils;
import com.rks.paymentservice.utility.RestMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.rks.paymentservice.constants.Constant.*;
import static com.rks.paymentservice.constants.ErrorCodeConstants.*;

@Component
public class OrderClientImpl implements OrderClient {

    private static final Logger logger = LogManager.getLogger(OrderClientImpl.class);

    @Value("${order-service-http-url}")
    private String orderServiceIp;

   /* @Override
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
    }*/

    @Override
    public OrderResponse getOrderDetails(Long orderId) {
        logger.info("Request received to get order details for orderId: {}", orderId);

        try {
            HttpHeaders httpHeaders = createHttpHeadersForOrderInquiry();
            final String url = orderServiceIp + "order-service/api/v1/orders/" + orderId;

            logger.debug("url: {}, headers: {}, service: {}", url, httpHeaders, ORDER_SERVICE);

            OrderResponse response = RestMethod.restRequest(httpHeaders, OrderResponse.class, url, ORDER_SERVICE, HttpMethod.GET, null);

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

    @Override
    public OrderResponse getOrderDetailsWithJwt(Long orderId) {
        logger.info("Request received to get order details for orderId: {}", orderId);

        try {
            String jwtToken = getJwTokenForOrderInquiry(orderId);
            HttpHeaders httpHeaders = createHttpHeadersForOrderInquiryWithJwt(jwtToken);

            final String url = orderServiceIp + "order-service/api/v1/orders/order-with-auth/" + orderId;

            logger.debug("url: {}, headers: {}, service: {}", url, httpHeaders, ORDER_SERVICE);

            OrderResponse response = RestMethod.restRequest(httpHeaders, OrderResponse.class, url, ORDER_SERVICE, HttpMethod.GET, null);

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

    private HttpHeaders createHttpHeadersForOrderInquiry() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set(JWT_CLIENT_ID, PAYMENT_SERVICE);
        return httpHeaders;
    }

    private HttpHeaders createHttpHeadersForOrderInquiryWithJwt(String jwtToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        httpHeaders.set(AUTHORIZATION, jwtToken);
        httpHeaders.set(JWT_CLIENT_ID, PAYMENT_SERVICE);
        return httpHeaders;
    }

    private String getJwTokenForOrderInquiry(Long orderId) {
        logger.info("Going to create new jwt token for order enquiry");
        try {
            String secret = JWT_SECRET_KEY_ORDER_SERVICE;
            // To test expired tokens - use below value for issuedAt
            //Date issuedAt = new Date(System.currentTimeMillis() - JWT_EXPIRATION_TIME * 2);
            Date issuedAt = new Date();
            Date expiresAt = new Date(issuedAt.getTime() + JWT_EXPIRATION_TIME);

            return JWT.create()
                    .withIssuer(PAYMENT_SERVICE)
                    .withClaim(JWT_ORDER_ID, String.valueOf(orderId))
                    .withClaim(JWT_CLIENT_ID, PAYMENT_SERVICE)
                    .withIssuedAt(issuedAt)
                    .withExpiresAt(expiresAt)
                    .sign(Algorithm.HMAC256(secret));
        } catch (Exception e) {
            logger.error("Exception: {} while generating jwt token.", CommonUtils.exceptionFormatter(e));
            throw new BaseException(Constant.FAILED, JWT_TOKEN_GENERATION_ERROR_CODE,
                    JWT_TOKEN_GENERATION_ERROR_MSG);
        }
    }
}
