package com.library.policy_service.dto;

import jakarta.validation.constraints.*;

/**
 * DTO for creating a new booking policy
 */
public class CreatePolicyRequest {
    
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;
    
    @NotNull(message = "Max duration in minutes is required")
    @Min(value = 1, message = "Max duration must be at least 1 minute")
    private Integer maxDurationMinutes;
    
    @NotNull(message = "Max advance days is required")
    @Min(value = 0, message = "Max advance days must be non-negative")
    private Integer maxAdvanceDays;
    
    @NotNull(message = "Max concurrent bookings is required")
    @Min(value = 1, message = "Max concurrent bookings must be at least 1")
    private Integer maxConcurrentBookings;
    
    @NotNull(message = "Grace period in minutes is required")
    @Min(value = 0, message = "Grace period must be non-negative")
    private Integer gracePeriodMinutes;
    
    // Getters and Setters
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
}

