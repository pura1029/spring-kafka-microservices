package com.gtm.spring.ecommerce.kafka.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


@Slf4j
@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic kafkaTestTopic() {
        return TopicBuilder.name("kafka-test")
                .partitions(3).replicas(2)
                .config(TopicConfig.RETENTION_MS_CONFIG, "1680000")
                .compact()
                .build();
    }
}
