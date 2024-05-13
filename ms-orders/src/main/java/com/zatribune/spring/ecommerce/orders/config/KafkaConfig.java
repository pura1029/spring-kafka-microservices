package com.zatribune.spring.ecommerce.orders.config;


import com.zatribune.spring.ecommerce.orders.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static domain.Topics.ORDERS;
import static domain.Topics.ORDER_STATUS;
import static domain.Topics.PAYMENTS;
import static domain.Topics.STOCK;


@Slf4j
@Configuration
public class KafkaConfig {


    private final OrderService orderService;


    @Autowired
    public KafkaConfig(OrderService orderService) {
        this.orderService = orderService;
    }

    @Bean
    public NewTopic orders() {
        return TopicBuilder.name(ORDERS)
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic paymentTopic() {
        return TopicBuilder.name(PAYMENTS)
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic stockTopic() {
        return TopicBuilder.name(STOCK)
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic orderStatusTopic() {
        return TopicBuilder.name(ORDER_STATUS)
                .partitions(3)
                .compact()
                .build();
    }
}
