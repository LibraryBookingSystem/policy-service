package com.library.policy_service.exception;

/**
 * Exception thrown when a booking request violates policy rules
 */
public class PolicyValidationException extends RuntimeException {
    
    public PolicyValidationException(String message) {
        super(message);
    }
}




