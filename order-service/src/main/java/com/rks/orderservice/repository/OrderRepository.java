package com.rks.orderservice.repository;

import com.rks.orderservice.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    @Query("from orders t where t.orderStatus = :orderStatus")
    List<Order> findByOrderStatus(@Param("orderStatus") String orderStatus);


}
