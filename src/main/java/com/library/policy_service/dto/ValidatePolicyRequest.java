package com.library.policy_service.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO for validating a booking request against policies
 */
public class ValidatePolicyRequest {
    
    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;
    
    @NotNull(message = "End time is required")
    private LocalDateTime endTime;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    private Integer currentBookingCount; // Number of active bookings for this user
    
    // Getters and Setters
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Integer getCurrentBookingCount() {
        return currentBookingCount;
    }
    
    public void setCurrentBookingCount(Integer currentBookingCount) {
        this.currentBookingCount = currentBookingCount;
    }
}

