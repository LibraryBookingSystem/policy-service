package com.library.policy_service.exception;

/**
 * Exception thrown when a policy is not found
 */
public class PolicyNotFoundException extends RuntimeException {
    
    public PolicyNotFoundException(String message) {
        super(message);
    }
}




