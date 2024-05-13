package com.zatribune.spring.ecommerce.orders.service;

import com.zatribune.spring.ecommerce.orders.db.entities.OrderDto;
import com.zatribune.spring.ecommerce.orders.db.repository.OrderRepository;
import domain.Order;
import domain.OrderSource;
import domain.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static domain.OrderSource.PAYMENT;
import static domain.OrderSource.STOCK;
import static domain.OrderStatus.ACCEPT;
import static domain.OrderStatus.CONFIRMED;
import static domain.OrderStatus.REJECT;
import static domain.OrderStatus.REJECTED;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order confirm(Order orderPayment, Order orderStock) {

        Order o = Order.builder()
                .id(orderPayment.getId())
                .customerId(orderPayment.getCustomerId())
                .productId(orderPayment.getProductId())
                .productCount(orderPayment.getProductCount())
                .price(orderPayment.getPrice())
                .build();

        if (orderPayment.getStatus().equals(ACCEPT) &&
                orderStock.getStatus().equals(ACCEPT)) {
            o.setStatus(CONFIRMED);
        } else if (orderPayment.getStatus().equals(REJECT) &&
                orderStock.getStatus().equals(REJECT)) {
            o.setStatus(REJECTED);
        } else if (orderPayment.getStatus().equals(REJECT) ||
                orderStock.getStatus().equals(REJECT)) {
            OrderSource source = orderPayment.getStatus().equals(REJECT)
                    ? PAYMENT : STOCK;
            o.setStatus(OrderStatus.ROLLBACK);
            o.setSource(source);
        }
        return o;
    }

    @Override
    public Order save(Order order) {
        OrderDto orderDto = repository.save(getOrderDto(order));
        return getOrder(orderDto);
    }

    @Override
    @Transactional
    public OrderDto getOrderDto(Order o) {
        return OrderDto.builder()
                .id(o.getId())
                .customerId(o.getCustomerId())
                .productId(o.getProductId())
                .productCount(o.getProductCount())
                .price(o.getPrice())
                .status(o.getStatus())
                .source(o.getSource())
                .build();
    }

    @Override
    @Transactional
    public Order getOrderDtoById(Long id) {
        OrderDto orderDto = repository.getById(id);
        log.info("{}", orderDto);
        return getOrder(orderDto);
    }

    private Order getOrder(OrderDto orderDto) {
        return Order.builder()
                .id(orderDto.getId())
                .customerId(orderDto.getCustomerId())
                .productId(orderDto.getProductId())
                .productCount(orderDto.getProductCount())
                .price(orderDto.getPrice())
                .status(orderDto.getStatus())
                .source(orderDto.getSource())
                .build();
    }
}
