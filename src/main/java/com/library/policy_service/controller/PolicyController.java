package com.library.policy_service.controller;

import com.library.policy_service.dto.*;
import com.library.policy_service.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for policy management endpoints
 */
@RestController
@RequestMapping("/api/policies")
public class PolicyController {
    
    private final PolicyService policyService;
    
    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }
    
    /**
     * Create a new policy
     * POST /api/policies
     */
    @PostMapping
    public ResponseEntity<PolicyResponse> createPolicy(@Valid @RequestBody CreatePolicyRequest request) {
        PolicyResponse response = policyService.createPolicy(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Get policy by ID
     * GET /api/policies/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PolicyResponse> getPolicyById(@PathVariable Long id) {
        PolicyResponse response = policyService.getPolicyById(id);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get all policies
     * GET /api/policies
     */
    @GetMapping
    public ResponseEntity<List<PolicyResponse>> getAllPolicies(
            @RequestParam(required = false) Boolean active) {
        List<PolicyResponse> policies;
        if (active != null && active) {
            policies = policyService.getActivePolicies();
        } else {
            policies = policyService.getAllPolicies();
        }
        return ResponseEntity.ok(policies);
    }
    
    /**
     * Update policy
     * PUT /api/policies/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<PolicyResponse> updatePolicy(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePolicyRequest request) {
        PolicyResponse response = policyService.updatePolicy(id, request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Delete policy
     * DELETE /api/policies/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        policyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Validate a booking request against policies
     * POST /api/policies/validate
     */
    @PostMapping("/validate")
    public ResponseEntity<PolicyValidationResponse> validateBooking(
            @Valid @RequestBody ValidatePolicyRequest request) {
        PolicyValidationResponse response = policyService.validateBooking(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Health check endpoint
     * GET /api/policies/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Policy Service is running!");
    }
}

