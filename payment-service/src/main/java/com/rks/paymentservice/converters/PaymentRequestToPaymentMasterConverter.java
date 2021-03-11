package com.rks.paymentservice.converters;

import com.rks.paymentservice.domain.PaymentMaster;
import com.rks.paymentservice.dto.request.PaymentRequest;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class PaymentRequestToPaymentMasterConverter implements Converter<PaymentRequest, PaymentMaster> {

    @Override
    public PaymentMaster convert(MappingContext<PaymentRequest, PaymentMaster> mappingContext) {
        mappingContext.getDestination().setOrderId(mappingContext.getSource().getOrderId());
        mappingContext.getDestination().setOrderDate(mappingContext.getSource().getOrderDate());
        mappingContext.getDestination().setPaymentDate(mappingContext.getSource().getPaymentDate());
        mappingContext.getDestination().setPaymentStatus(mappingContext.getSource().getPaymentStatus());
        return mappingContext.getDestination();
    }
}
