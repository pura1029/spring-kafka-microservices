/*
 * Copyright (c) 2024 VMware, Inc. All Rights Reserved.
 *
 */

package com.gtm.spring.ecommerce.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.UUID;

/**
 * @author kumargautam
 */
@Component
public class KafkaDataTest {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaDataTest(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    public void push() {
        for (int i = 0; i < 10; i++) {
            String id = UUID.randomUUID().toString();
            kafkaTemplate.send("kafka-test", id, "Hello Kafka-" + id + " " + i);
        }
    }
}
