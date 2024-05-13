package com.zatribune.spring.ecommerce.orders.listener;


import com.zatribune.spring.ecommerce.orders.service.OrderService;
import domain.KafkaGroupIds;
import domain.KafkaIds;
import domain.Order;
import domain.Topics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderListener {

    private final OrderService orderService;

    @Autowired
    public OrderListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(id = KafkaIds.ORDERS, topics = Topics.ORDER_STATUS, groupId = KafkaGroupIds.ORDER_STATE, concurrency = "2")
    public void onEvent(Order o) {
        log.info("onEvent Received: {}", o);
        orderService.save(o);
    }
}
