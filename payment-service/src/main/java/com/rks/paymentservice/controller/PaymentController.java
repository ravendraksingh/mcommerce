package com.rks.paymentservice.controller;

import com.rks.paymentservice.domain.PaymentMaster;
import com.rks.paymentservice.dto.order.OrderResponse;
import com.rks.paymentservice.dto.response.PaymentMasterResponse;
import com.rks.paymentservice.repository.PaymentMasterRepository;
import com.rks.paymentservice.service.IPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/payment-service/api/v1")
public class PaymentController {

    public static final Logger log = LoggerFactory.getLogger(PaymentMaster.class);

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private PaymentMasterRepository paymentMasterRepository;

   /* @GetMapping("/payments")
    public List<PaymentMaster> getAllPayments() {
        List<PaymentMaster> paymentMasterList = new ArrayList<>();
        paymentMasterRepository.findAll().forEach(paymentMasterList::add);
        if (paymentMasterList == null) {
            log.error("No records found");
            throw new RuntimeException("No records found.");
        }
        return paymentMasterList;
    }*/

    @GetMapping("/payments/{paymentId}")
    public PaymentMasterResponse getPaymentMasterDataForPaymentId(@PathVariable(value="paymentId") Long paymentId) {
        return paymentService.getPaymentMasterDataByPaymentId(paymentId);
    }

    @PostMapping(path = "/payments")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMasterResponse createPayment(@RequestBody PaymentMaster request) {
        return paymentService.createPayment(request);
    }

    @GetMapping("/orders/{orderId}")
    public OrderResponse getOrderDetailsRemote(@PathVariable("orderId") Long orderId) {
        return paymentService.getOrderDetailsRemote(orderId);
    }

    @GetMapping("/orders/order-with-auth/{orderId}")
    public OrderResponse getOrderDetailsRemoteWithJwt(@PathVariable("orderId") Long orderId) {
        return paymentService.getOrderDetailsRemoteWithJwt(orderId);
    }
}
