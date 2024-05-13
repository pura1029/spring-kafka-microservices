package com.gtm.spring.ecommerce.stock.db;


import com.gtm.spring.ecommerce.stock.db.repository.ProductRepository;
import com.gtm.spring.ecommerce.stock.db.entities.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


@Slf4j
@Component
public class DevBootstrap {


    private final ProductRepository repository;

    @Autowired
    public DevBootstrap(ProductRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void run() {
        Product p1 = Product.builder().name("Chicken")
                .availableItems(5000)
                .reservedItems(0)
                .build();

        Product p2 = Product.builder().name("Rice")
                .availableItems(3500)
                .reservedItems(0)
                .build();

        Product p3 = Product.builder().name("Spaghetti")
                .availableItems(100)
                .reservedItems(0)
                .build();

        Product p4 = Product.builder().name("Salad")
                .availableItems(120)
                .reservedItems(0)
                .build();

        repository.saveAll(List.of(p1, p2, p3, p4));
    }
}
