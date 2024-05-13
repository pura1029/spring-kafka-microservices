package com.gtm.spring.ecommerce.payments.listener;


import domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.gtm.spring.ecommerce.payments.service.OrderService;

@Slf4j
@Component
public class OrderListener {


    private final OrderService orderService;

    @Autowired
    public OrderListener(OrderService orderService) {
        this.orderService = orderService;
    }


    @KafkaListener(id = KafkaIds.ORDERS, topics = Topics.ORDERS, groupId = KafkaGroupIds.PAYMENTS)
    public void onEvent(Order o) {
        log.info("onEvent Received: {}" , o);
        if (o.getStatus().equals(OrderStatus.NEW))
            orderService.reserve(o);
    }

    @KafkaListener(id = KafkaIds.ORDERS_STATUS, topics = Topics.STOCK, groupId = KafkaGroupIds.PAYMENTS_STATUS)
    public void onEventHavingStock(Order o) {
        log.info("onEventHavingStock Received: {}" , o);
        if (o.getStatus().equals(OrderStatus.ACCEPT) || o.getStatus().equals(OrderStatus.REJECT))
            orderService.confirm(o);
    }
}
