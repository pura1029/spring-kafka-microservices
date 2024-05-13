package com.zatribune.spring.ecommerce.orders.controller;


import com.zatribune.spring.ecommerce.orders.service.OrderService;
import domain.Order;
import domain.OrderSource;
import domain.OrderStatus;
import domain.Topics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RequestMapping("/orders")
@RestController
public class OrderController {

    private final KafkaTemplate<Long, Order> kafkaTemplate;
    private final OrderService orderService;


    @Autowired
    public OrderController(KafkaTemplate<Long, Order> kafkaTemplate, OrderService orderService) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderService = orderService;
    }

    @PostMapping
    public Order create(@RequestBody Order order) throws ExecutionException, InterruptedException {
        order.setStatus(OrderStatus.NEW);
        order.setSource(OrderSource.ORDER);
        log.info("Sent: {}", order);
        order = orderService.save(order);
        kafkaTemplate.send(Topics.ORDERS, order.getId(), order);
        return order;
    }

    @GetMapping
    public List<Order> all() {
        List<Order> orders = new ArrayList<>();
        /*ReadOnlyKeyValueStore<Long, Order> store = kafkaStreamsFactory
                .getKafkaStreams()
                .store(StoreQueryParameters.fromNameAndType(
                        Topics.ORDERS,
                        QueryableStoreTypes.keyValueStore()));
        KeyValueIterator<Long, Order> it = store.all();
        it.forEachRemaining(kv -> orders.add(kv.value));*/
        return orders;
    }

    @GetMapping("/{id}")
    public Order getOrderStatusById(@PathVariable Long id) {
        return orderService.getOrderDtoById(id);
    }
}
