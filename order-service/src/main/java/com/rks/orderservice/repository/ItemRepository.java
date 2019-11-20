package com.rks.orderservice.repository;

import com.rks.orderservice.domain.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

}
