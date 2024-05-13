package com.gtm.spring.ecommerce.kafka.consumer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaListenerTest {


    @KafkaListener(id = "kafka-test-id", topics = "kafka-test", groupId = "kafka-test-group", concurrency = "2")
    public void onEvent(String o) {

        log.info("onEvent Received: {}", o);
    }
}
