package com.rks.paymentservice.mapper;

import com.rks.paymentservice.converters.PaymentRequestToPaymentMasterConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper extends ModelMapper {
    public PaymentMapper() {
        PaymentRequestToPaymentMasterConverter c1 = new PaymentRequestToPaymentMasterConverter();
        this.addConverter(c1);
    }
}
