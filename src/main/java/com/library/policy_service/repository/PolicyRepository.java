package com.library.policy_service.repository;

import com.library.policy_service.entity.BookingPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for BookingPolicy entity
 */
@Repository
public interface PolicyRepository extends JpaRepository<BookingPolicy, Long> {
    
    /**
     * Find policy by name
     */
    Optional<BookingPolicy> findByName(String name);
    
    /**
     * Find all active policies
     */
    List<BookingPolicy> findByIsActiveTrue();
    
    /**
     * Check if policy exists by name
     */
    boolean existsByName(String name);
}




