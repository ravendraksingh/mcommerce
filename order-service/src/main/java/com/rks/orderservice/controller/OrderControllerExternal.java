package com.rks.orderservice.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rks.orderservice.domain.Order;
import com.rks.orderservice.dto.request.OrderRequest;
import com.rks.orderservice.dto.response.ErrorResponse;
import com.rks.orderservice.dto.response.OrderResponse;
import com.rks.orderservice.exception.UnauthorizedAccessException;
import com.rks.orderservice.service.OrderService;
import com.rks.orderservice.utility.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.rks.orderservice.constants.Constant.*;

@Api(value = "Order Service", description = "All APIs of order service")
@RestController
@RequestMapping("/order-service/ext")
public class OrderControllerExternal {

    public static final Logger log = LoggerFactory.getLogger(OrderControllerExternal.class);

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.findAllOrders();
    }

    @GetMapping("/orders/{orderId}")
    public OrderResponse getOrder(@PathVariable(value="orderId") Long orderId) {
        return orderService.findOrderById(orderId);
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createNewOrder(orderRequest);
    }


    @ApiOperation(
            httpMethod = "GET",
            value = "Get Order Details",
            response = OrderResponse.class,
            notes = "Please provide access token in the header."
    )
    @RequestMapping(
            value = "/v1/orders/{orderId}",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 400,
                    message = "Bad Request",
                    response = ErrorResponse.class
            ),
            @ApiResponse(
                    code = 401,
                    message = "Unauthorized",
                    response = ErrorResponse.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "Internal Service Error",
                    response = ErrorResponse.class
            )
    })
    public OrderResponse getOrderWithJwt(@PathVariable("orderId") Long orderId,
                                         @RequestHeader(CLIENT_ID) String clientId,
                                         @RequestHeader(AUTHORIZATION) String jwtToken) {
        validateToken(clientId, orderId, jwtToken);
        return orderService.findOrderById(orderId);
    }


    private void validateToken(String clientId, Long orderId, String jwtToken) {
        try {
            String secret = JWT_SECRET_KEY_ORDER_SERVICE;
            log.info("Verifying jwt token");

            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("payment-service")
                    .withClaim("orderId", String.valueOf(orderId))
                    .withClaim(CLIENT_ID, clientId)
                    .build();

            DecodedJWT jwt = jwtVerifier.verify(jwtToken);
            log.info("Jwt token verification successful");

            /*System.out.println(clientId + " : " + orderId);
            System.out.println(jwt.getClaim(CLIENT_ID).asString());
            System.out.println(jwt.getClaim("orderId").asString());*/

            log.info("Matching Client-id and orderId values provided in header and token");
            if (jwt.getClaim(CLIENT_ID).asString().equalsIgnoreCase(clientId) &&
                    jwt.getClaim("orderId").asString().equals(String.valueOf(orderId))) {
                log.info("Client-id {} in header matches with Client-id {} in jwt token", clientId, jwt.getClaim(CLIENT_ID).asString());
                log.info("orderId {} in header matches with orderId {} in jwt token", orderId, jwt.getClaim("orderId").asString());
            }
        } catch (IllegalArgumentException | UnsupportedEncodingException | JWTVerificationException e) {
            log.error("Error while verifying JWT token", CommonUtils.exceptionFormatter(e));
            throw new UnauthorizedAccessException(FAILED, 401, CommonUtils.exceptionFormatter(e));
        } /*catch (Exception e) {
            BaseException ex = new BaseException(FAILED, INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR_MSG);
            log.error("Exception occurred while verifying jwt token for Client-id {} and orderId {}. ResponseCode: {}. Message: {}",
                    clientId, orderId, ex.getCode(), ex.getMessage());
            throw ex;
        }*/
    }
}
