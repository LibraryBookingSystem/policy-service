package com.library.policy_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class for Policy Service
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.library.policy_service", "com.library.common"})
public class PolicyServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PolicyServiceApplication.class, args);
    }
}





