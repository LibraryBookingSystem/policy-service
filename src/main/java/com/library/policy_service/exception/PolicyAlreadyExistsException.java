package com.library.policy_service.exception;

/**
 * Exception thrown when a policy with the same name already exists
 */
public class PolicyAlreadyExistsException extends RuntimeException {
    
    public PolicyAlreadyExistsException(String message) {
        super(message);
    }
}




