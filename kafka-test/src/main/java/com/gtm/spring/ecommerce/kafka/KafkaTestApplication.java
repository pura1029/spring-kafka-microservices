package com.gtm.spring.ecommerce.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KafkaTestApplication {

    public static void main(String[] args) {

        SpringApplication.run(KafkaTestApplication.class, args);
    }
}