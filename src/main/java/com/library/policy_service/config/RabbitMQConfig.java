package com.library.policy_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ configuration for event publishing
 */
@Configuration
public class RabbitMQConfig {
    
    // Exchange names
    public static final String POLICY_EXCHANGE = "policy.events";
    
    // Queue names
    public static final String POLICY_CREATED_QUEUE = "policy.created";
    public static final String POLICY_UPDATED_QUEUE = "policy.updated";
    public static final String POLICY_DELETED_QUEUE = "policy.deleted";
    
    // Routing keys
    public static final String POLICY_CREATED_ROUTING_KEY = "policy.created";
    public static final String POLICY_UPDATED_ROUTING_KEY = "policy.updated";
    public static final String POLICY_DELETED_ROUTING_KEY = "policy.deleted";
    
    /**
     * Create topic exchange for policy events
     */
    @Bean
    public TopicExchange policyExchange() {
        return new TopicExchange(POLICY_EXCHANGE);
    }
    
    /**
     * Create queue for policy created events
     */
    @Bean
    public Queue policyCreatedQueue() {
        return new Queue(POLICY_CREATED_QUEUE, true);
    }
    
    /**
     * Create queue for policy updated events
     */
    @Bean
    public Queue policyUpdatedQueue() {
        return new Queue(POLICY_UPDATED_QUEUE, true);
    }
    
    /**
     * Create queue for policy deleted events
     */
    @Bean
    public Queue policyDeletedQueue() {
        return new Queue(POLICY_DELETED_QUEUE, true);
    }
    
    /**
     * Bind policy created queue to exchange
     */
    @Bean
    public Binding policyCreatedBinding() {
        return BindingBuilder
            .bind(policyCreatedQueue())
            .to(policyExchange())
            .with(POLICY_CREATED_ROUTING_KEY);
    }
    
    /**
     * Bind policy updated queue to exchange
     */
    @Bean
    public Binding policyUpdatedBinding() {
        return BindingBuilder
            .bind(policyUpdatedQueue())
            .to(policyExchange())
            .with(POLICY_UPDATED_ROUTING_KEY);
    }
    
    /**
     * Bind policy deleted queue to exchange
     */
    @Bean
    public Binding policyDeletedBinding() {
        return BindingBuilder
            .bind(policyDeletedQueue())
            .to(policyExchange())
            .with(POLICY_DELETED_ROUTING_KEY);
    }
    
    /**
     * JSON message converter
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    /**
     * RabbitTemplate with JSON converter
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}




