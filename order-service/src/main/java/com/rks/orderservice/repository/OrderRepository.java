package com.rks.orderservice.repository;

import com.rks.orderservice.domain.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    //List<Order> findByStatus(@Param("status") Status status);
}
