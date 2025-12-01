package com.library.policy_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing booking policies
 */
@Entity
@Table(name = "booking_policies")
public class BookingPolicy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(name = "max_duration_minutes", nullable = false)
    private Integer maxDurationMinutes;
    
    @Column(name = "max_advance_days", nullable = false)
    private Integer maxAdvanceDays;
    
    @Column(name = "max_concurrent_bookings", nullable = false)
    private Integer maxConcurrentBookings;
    
    @Column(name = "grace_period_minutes", nullable = false)
    private Integer gracePeriodMinutes;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public BookingPolicy() {}
    
    public BookingPolicy(String name, Integer maxDurationMinutes, Integer maxAdvanceDays,
                        Integer maxConcurrentBookings, Integer gracePeriodMinutes) {
        this.name = name;
        this.maxDurationMinutes = maxDurationMinutes;
        this.maxAdvanceDays = maxAdvanceDays;
        this.maxConcurrentBookings = maxConcurrentBookings;
        this.gracePeriodMinutes = gracePeriodMinutes;
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

