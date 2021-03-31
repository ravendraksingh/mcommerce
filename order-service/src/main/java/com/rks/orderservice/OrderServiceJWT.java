package com.rks.orderservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rks.mcommon.utility.CommonUtils;
import com.rks.orderservice.exception.BaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

import static com.rks.mcommon.constants.CommonConstants.FAILED;
import static com.rks.orderservice.constants.OrderServiceConstants.*;
import static com.rks.orderservice.constants.OrderServiceErrorCodes.JWT_TOKEN_GENERATION_ERROR_CODE;

public class OrderServiceJWT {

    private static final Logger log = LogManager.getLogger(OrderServiceJWT.class);

    private String getJwTokenForOrderInquiry(Long orderId) {
        log.info("Going to create new jwt token for order enquiry");
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
            log.error("Exception: {} while generating jwt token.", CommonUtils.exceptionFormatter(e));
            throw new BaseException(FAILED, JWT_TOKEN_GENERATION_ERROR_CODE,
                    JWT_TOKEN_GENERATION_ERROR_MSG);
        }
    }
}
