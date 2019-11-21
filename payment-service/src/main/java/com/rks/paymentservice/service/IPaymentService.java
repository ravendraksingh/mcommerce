package com.rks.paymentservice.service;

import com.rks.paymentservice.domain.PaymentMaster;
import com.rks.paymentservice.dto.response.PaymentMasterResponse;

import java.util.List;

public interface IPaymentService {

    PaymentMasterResponse getPaymentMasterDataByPaymentId(Long paymentId);

    //List<PaymentMasterResponse> findPaymentMasterDataByPaymentId(Long paymentId);

    PaymentMasterResponse createPayment(PaymentMaster request);

    PaymentMasterResponse createPaymentForNewOrder(Long orderId);
}
