package com.library.policy_service.controller;

import com.library.policy_service.dto.*;
import com.library.common.security.annotation.RequiresRole;
import com.library.policy_service.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for policy management endpoints
 * Uses AOP annotations for RBAC authorization
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
     * Authorization: ADMIN only
     */
    @PostMapping
    @RequiresRole({ "ADMIN" })
    public ResponseEntity<PolicyResponse> createPolicy(@Valid @RequestBody CreatePolicyRequest request) {
        PolicyResponse response = policyService.createPolicy(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get policy by ID
     * GET /api/policies/{id}
     * Authorization: AUTHENTICATED
     */
    @GetMapping("/{id}")
    public ResponseEntity<PolicyResponse> getPolicyById(@PathVariable Long id) {
        PolicyResponse response = policyService.getPolicyById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all policies
     * GET /api/policies
     * Authorization: AUTHENTICATED
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
     * Authorization: ADMIN only
     */
    @PutMapping("/{id}")
    @RequiresRole({ "ADMIN" })
    public ResponseEntity<PolicyResponse> updatePolicy(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePolicyRequest request) {
        PolicyResponse response = policyService.updatePolicy(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete policy
     * DELETE /api/policies/{id}
     * Authorization: ADMIN only
     */
    @DeleteMapping("/{id}")
    @RequiresRole({ "ADMIN" })
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        policyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Validate a booking request against policies
     * POST /api/policies/validate
     * Authorization: PUBLIC (Internal service call - called by booking-service)
     * Note: No @RequiresRole annotation - this is a public endpoint for
     * inter-service calls
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
     * Authorization: PUBLIC
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Policy Service is running!");
    }
}
