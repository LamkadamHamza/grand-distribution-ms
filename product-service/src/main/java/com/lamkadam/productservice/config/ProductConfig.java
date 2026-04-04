package com.lamkadam.productservice.config;

import com.lamkadam.productservice.entity.Product;
import com.lamkadam.productservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ProductConfig {

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                productRepository.save(new

                        Product(null, "Laptop", 1200.0, 10));
                productRepository.save(new Product(null, "Mouse", 150.0, 30));
                productRepository.save(new Product(null, "Keyboard", 300.0, 20));
            }
        };
    }
}
