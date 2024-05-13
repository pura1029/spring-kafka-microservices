package com.zatribune.spring.ecommerce.orders.service;

import com.zatribune.spring.ecommerce.orders.db.entities.OrderDto;
import domain.Order;

public interface OrderService {

    Order confirm(Order orderPayment, Order orderStock);

    Order save(Order order);

    OrderDto getOrderDto(Order o);

    Order getOrderDtoById(Long id);
}
