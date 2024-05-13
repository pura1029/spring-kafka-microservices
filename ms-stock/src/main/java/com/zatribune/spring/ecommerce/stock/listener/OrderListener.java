package com.zatribune.spring.ecommerce.stock.listener;


import domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.zatribune.spring.ecommerce.stock.service.OrderService;

@Slf4j
@Component
public class OrderListener {

    private final OrderService orderService;

    @Autowired
    public OrderListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(id = KafkaIds.ORDERS, topics = Topics.ORDERS, groupId = KafkaGroupIds.STOCK)
    public void onEvent(Order o) {
        log.info("onEvent Received: {}" , o);
        if (o.getStatus().equals(OrderStatus.NEW))
            orderService.reserve(o);
    }

    @KafkaListener(id = KafkaIds.ORDERS_STATUS, topics = Topics.PAYMENTS, groupId = KafkaGroupIds.STOCK_STATUS)
    public void onEventForPayment(Order o) {
        log.info("onEventForPayment Received: {}" , o);
        if (o.getStatus().equals(OrderStatus.CONFIRMED) || o.getStatus().equals(OrderStatus.ROLLBACK))
            orderService.confirm(o);
    }
}
