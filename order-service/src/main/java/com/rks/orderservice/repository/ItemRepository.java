package com.rks.orderservice.repository;

import com.rks.orderservice.domain.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
}
