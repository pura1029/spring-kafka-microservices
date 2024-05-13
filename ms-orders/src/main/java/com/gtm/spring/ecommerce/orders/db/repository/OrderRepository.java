package com.gtm.spring.ecommerce.orders.db.repository;

import com.gtm.spring.ecommerce.orders.db.entities.OrderDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderDto, Long> {

    OrderDto getById(Long id);
}