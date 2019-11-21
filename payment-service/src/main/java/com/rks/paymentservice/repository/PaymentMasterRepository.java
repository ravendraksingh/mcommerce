package com.rks.paymentservice.repository;

import com.rks.paymentservice.domain.PaymentMaster;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaymentMasterRepository extends PagingAndSortingRepository<PaymentMaster, Long> {
}
