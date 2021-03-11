package com.rks.paymentservice.service;

import com.rks.paymentservice.domain.PaymentMaster;
import com.rks.paymentservice.dto.order.OrderResponse;
import com.rks.paymentservice.dto.request.PaymentRequest;
import com.rks.paymentservice.dto.response.PaymentMasterResponse;

public interface IPaymentService {

    PaymentMasterResponse getPaymentMasterDataByPaymentId(Long paymentId);

    //List<PaymentMasterResponse> findPaymentMasterDataByPaymentId(Long paymentId);

    PaymentMasterResponse createPayment(PaymentRequest request);

    PaymentMasterResponse createPaymentForNewOrder(Long orderId);

    OrderResponse getOrderDetailsRemote(Long orderId);

    OrderResponse getOrderDetailsRemoteWithJwt(Long orderId);

    OrderResponse getOrderDetailsRemoteWithJwtToken(Long orderId, String jwtToken);
}
