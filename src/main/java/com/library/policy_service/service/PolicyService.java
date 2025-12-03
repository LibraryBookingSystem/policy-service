package com.library.policy_service.service;

import com.library.policy_service.dto.*;
import com.library.policy_service.entity.BookingPolicy;
import com.library.policy_service.exception.PolicyAlreadyExistsException;
import com.library.policy_service.exception.PolicyNotFoundException;
import com.library.policy_service.repository.PolicyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for policy operations
 */
@Service
public class PolicyService {
    
    private static final Logger logger = LoggerFactory.getLogger(PolicyService.class);
    
    private final PolicyRepository policyRepository;
    private final PolicyEventPublisher eventPublisher;
    
    public PolicyService(PolicyRepository policyRepository,
                         PolicyEventPublisher eventPublisher) {
        this.policyRepository = policyRepository;
        this.eventPublisher = eventPublisher;
    }
    
    /**
     * Create a new policy
     */
    @Transactional
    public PolicyResponse createPolicy(CreatePolicyRequest request) {
        logger.info("Creating policy: {}", request.getName());
        
        if (policyRepository.existsByName(request.getName())) {
            throw new PolicyAlreadyExistsException("Policy with name already exists: " + request.getName());
        }
        
        BookingPolicy policy = new BookingPolicy();
        policy.setName(request.getName());
        policy.setMaxDurationMinutes(request.getMaxDurationMinutes());
        policy.setMaxAdvanceDays(request.getMaxAdvanceDays());
        policy.setMaxConcurrentBookings(request.getMaxConcurrentBookings());
        policy.setGracePeriodMinutes(request.getGracePeriodMinutes());
        policy.setIsActive(true);
        
        policy = policyRepository.save(policy);
        logger.info("Policy created successfully: {} (ID: {})", policy.getName(), policy.getId());
        
        PolicyResponse response = PolicyResponse.fromPolicy(policy);
        eventPublisher.publishPolicyCreated(response);
        
        return response;
    }
    
    /**
     * Get policy by ID
     */
    public PolicyResponse getPolicyById(Long id) {
        BookingPolicy policy = policyRepository.findById(id)
            .orElseThrow(() -> new PolicyNotFoundException("Policy not found with id: " + id));
        return PolicyResponse.fromPolicy(policy);
    }
    
    /**
     * Get all policies
     */
    public List<PolicyResponse> getAllPolicies() {
        return policyRepository.findAll().stream()
            .map(PolicyResponse::fromPolicy)
            .collect(Collectors.toList());
    }
    
    /**
     * Get all active policies
     */
    public List<PolicyResponse> getActivePolicies() {
        return policyRepository.findByIsActiveTrue().stream()
            .map(PolicyResponse::fromPolicy)
            .collect(Collectors.toList());
    }
    
    /**
     * Get the default/active policy (for validation)
     * In a real system, you might have multiple policies for different user roles
     */
    public BookingPolicy getActivePolicy() {
        List<BookingPolicy> activePolicies = policyRepository.findByIsActiveTrue();
        if (activePolicies.isEmpty()) {
            throw new PolicyNotFoundException("No active policy found");
        }
        // Return the first active policy (you could add logic to select by priority/role)
        return activePolicies.get(0);
    }
    
    /**
     * Update policy
     */
    @Transactional
    public PolicyResponse updatePolicy(Long id, UpdatePolicyRequest request) {
        logger.info("Updating policy: {}", id);
        
        BookingPolicy policy = policyRepository.findById(id)
            .orElseThrow(() -> new PolicyNotFoundException("Policy not found with id: " + id));
        
        if (request.getName() != null && !request.getName().isBlank()) {
            if (!policy.getName().equals(request.getName()) && 
                policyRepository.existsByName(request.getName())) {
                throw new PolicyAlreadyExistsException("Policy with name already exists: " + request.getName());
            }
            policy.setName(request.getName());
        }
        
        if (request.getMaxDurationMinutes() != null) {
            policy.setMaxDurationMinutes(request.getMaxDurationMinutes());
        }
        
        if (request.getMaxAdvanceDays() != null) {
            policy.setMaxAdvanceDays(request.getMaxAdvanceDays());
        }
        
        if (request.getMaxConcurrentBookings() != null) {
            policy.setMaxConcurrentBookings(request.getMaxConcurrentBookings());
        }
        
        if (request.getGracePeriodMinutes() != null) {
            policy.setGracePeriodMinutes(request.getGracePeriodMinutes());
        }
        
        if (request.getIsActive() != null) {
            policy.setIsActive(request.getIsActive());
        }
        
        policy = policyRepository.save(policy);
        logger.info("Policy updated successfully: {} (ID: {})", policy.getName(), policy.getId());
        
        PolicyResponse response = PolicyResponse.fromPolicy(policy);
        eventPublisher.publishPolicyUpdated(response);
        
        return response;
    }
    
    /**
     * Delete policy
     */
    @Transactional
    public void deletePolicy(Long id) {
        logger.info("Deleting policy: {}", id);
        
        BookingPolicy policy = policyRepository.findById(id)
            .orElseThrow(() -> new PolicyNotFoundException("Policy not found with id: " + id));
        
        Long policyId = policy.getId();
        policyRepository.delete(policy);
        logger.info("Policy deleted successfully: {} (ID: {})", policy.getName(), id);
        
        eventPublisher.publishPolicyDeleted(policyId);
    }
    
    /**
     * Validate a booking request against active policies
     */
    public PolicyValidationResponse validateBooking(ValidatePolicyRequest request) {
        logger.info("Validating booking request for user: {}", request.getUserId());
        
        BookingPolicy policy = getActivePolicy();
        PolicyValidationResponse validation = new PolicyValidationResponse();
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime = request.getStartTime();
        LocalDateTime endTime = request.getEndTime();
        
        // Check booking duration
        long durationMinutes = Duration.between(startTime, endTime).toMinutes();
        if (durationMinutes > policy.getMaxDurationMinutes()) {
            validation.addViolation(String.format(
                "Booking duration (%d minutes) exceeds maximum allowed duration (%d minutes)",
                durationMinutes, policy.getMaxDurationMinutes()
            ));
        }
        
        // Check advance booking window
        long daysInAdvance = Duration.between(now, startTime).toDays();
        if (daysInAdvance > policy.getMaxAdvanceDays()) {
            validation.addViolation(String.format(
                "Booking start time is %d days in advance, which exceeds maximum allowed (%d days)",
                daysInAdvance, policy.getMaxAdvanceDays()
            ));
        }
        
        // Check if booking is in the past
        if (startTime.isBefore(now)) {
            validation.addViolation("Booking start time cannot be in the past");
        }
        
        // Check if end time is before start time
        if (endTime.isBefore(startTime) || endTime.isEqual(startTime)) {
            validation.addViolation("Booking end time must be after start time");
        }
        
        // Check concurrent bookings limit
        if (request.getCurrentBookingCount() != null && 
            request.getCurrentBookingCount() >= policy.getMaxConcurrentBookings()) {
            validation.addViolation(String.format(
                "User has reached maximum concurrent bookings limit (%d)",
                policy.getMaxConcurrentBookings()
            ));
        }
        
        logger.info("Policy validation completed. Valid: {}", validation.isValid());
        return validation;
    }
}




