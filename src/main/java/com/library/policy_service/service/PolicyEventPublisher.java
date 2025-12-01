package com.library.policy_service.service;

import com.library.policy_service.config.RabbitMQConfig;
import com.library.policy_service.dto.PolicyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Service for publishing policy events to RabbitMQ
 */
@Service
public class PolicyEventPublisher {
    
    private static final Logger logger = LoggerFactory.getLogger(PolicyEventPublisher.class);
    
    private final RabbitTemplate rabbitTemplate;
    
    public PolicyEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    public void publishPolicyCreated(PolicyResponse policy) {
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.POLICY_EXCHANGE,
                RabbitMQConfig.POLICY_CREATED_ROUTING_KEY,
                policy
            );
            logger.info("Published policy.created event for policy: {}", policy.getId());
        } catch (Exception e) {
            logger.error("Failed to publish policy.created event: {}", e.getMessage());
        }
    }
    
    public void publishPolicyUpdated(PolicyResponse policy) {
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.POLICY_EXCHANGE,
                RabbitMQConfig.POLICY_UPDATED_ROUTING_KEY,
                policy
            );
            logger.info("Published policy.updated event for policy: {}", policy.getId());
        } catch (Exception e) {
            logger.error("Failed to publish policy.updated event: {}", e.getMessage());
        }
    }
    
    public void publishPolicyDeleted(Long policyId) {
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.POLICY_EXCHANGE,
                RabbitMQConfig.POLICY_DELETED_ROUTING_KEY,
                policyId
            );
            logger.info("Published policy.deleted event for policy: {}", policyId);
        } catch (Exception e) {
            logger.error("Failed to publish policy.deleted event: {}", e.getMessage());
        }
    }
}

