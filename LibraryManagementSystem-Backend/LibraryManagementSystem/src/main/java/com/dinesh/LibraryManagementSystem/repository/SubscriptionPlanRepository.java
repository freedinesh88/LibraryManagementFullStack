package com.dinesh.LibraryManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinesh.LibraryManagementSystem.model.SubscriptionPlan;

public interface SubscriptionPlanRepository
        extends JpaRepository<SubscriptionPlan, Long> {

    Optional<SubscriptionPlan> findByPlanCode(String planCode);

    boolean existsByPlanCode(String planCode);

    List<SubscriptionPlan> findByIsActiveTrueOrderByDisplayOrderAsc();

    List<SubscriptionPlan> findAllByOrderByDisplayOrderAsc();
}