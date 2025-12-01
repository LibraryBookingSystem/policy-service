package com.library.policy_service.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for policy validation response
 */
public class PolicyValidationResponse {
    
    private boolean valid;
    private List<String> violations;
    
    public PolicyValidationResponse() {
        this.valid = true;
        this.violations = new ArrayList<>();
    }
    
    public PolicyValidationResponse(boolean valid, List<String> violations) {
        this.valid = valid;
        this.violations = violations != null ? violations : new ArrayList<>();
    }
    
    public void addViolation(String violation) {
        this.valid = false;
        this.violations.add(violation);
    }
    
    // Getters and Setters
    public boolean isValid() {
        return valid;
    }
    
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    public List<String> getViolations() {
        return violations;
    }
    
    public void setViolations(List<String> violations) {
        this.violations = violations;
    }
}

