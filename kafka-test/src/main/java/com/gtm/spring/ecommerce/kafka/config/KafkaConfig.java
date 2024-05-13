package com.gtm.spring.ecommerce.kafka.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


@Slf4j
@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic orders() {
        return TopicBuilder.name("kafka-test")
                .partitions(3)
                .compact()
                .build();
    }
}
