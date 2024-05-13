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
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int id = random.nextInt(100);
            kafkaTemplate.send("kafka-test", String.valueOf(id), "Hello Kafka-" + id + " " + i);
        }
    }
}
