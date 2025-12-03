package com.library.policy_service.dto;

import com.library.policy_service.entity.BookingPolicy;
import java.time.LocalDateTime;

/**
 * DTO for policy response
 */
public class PolicyResponse {
    
    private Long id;
    private String name;
    private Integer maxDurationMinutes;
    private Integer maxAdvanceDays;
    private Integer maxConcurrentBookings;
    private Integer gracePeriodMinutes;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public PolicyResponse() {}
    
    public PolicyResponse(Long id, String name, Integer maxDurationMinutes, Integer maxAdvanceDays,
                         Integer maxConcurrentBookings, Integer gracePeriodMinutes, Boolean isActive,
                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.maxDurationMinutes = maxDurationMinutes;
        this.maxAdvanceDays = maxAdvanceDays;
        this.maxConcurrentBookings = maxConcurrentBookings;
        this.gracePeriodMinutes = gracePeriodMinutes;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    /**
     * Convert BookingPolicy entity to PolicyResponse DTO
     */
    public static PolicyResponse fromPolicy(BookingPolicy policy) {
        return new PolicyResponse(
            policy.getId(),
            policy.getName(),
            policy.getMaxDurationMinutes(),
            policy.getMaxAdvanceDays(),
            policy.getMaxConcurrentBookings(),
            policy.getGracePeriodMinutes(),
            policy.getIsActive(),
            policy.getCreatedAt(),
            policy.getUpdatedAt()
        );
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getMaxDurationMinutes() {
        return maxDurationMinutes;
    }
    
    public void setMaxDurationMinutes(Integer maxDurationMinutes) {
        this.maxDurationMinutes = maxDurationMinutes;
    }
    
    public Integer getMaxAdvanceDays() {
        return maxAdvanceDays;
    }
    
    public void setMaxAdvanceDays(Integer maxAdvanceDays) {
        this.maxAdvanceDays = maxAdvanceDays;
    }
    
    public Integer getMaxConcurrentBookings() {
        return maxConcurrentBookings;
    }
    
    public void setMaxConcurrentBookings(Integer maxConcurrentBookings) {
        this.maxConcurrentBookings = maxConcurrentBookings;
    }
    
    public Integer getGracePeriodMinutes() {
        return gracePeriodMinutes;
    }
    
    public void setGracePeriodMinutes(Integer gracePeriodMinutes) {
        this.gracePeriodMinutes = gracePeriodMinutes;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}




