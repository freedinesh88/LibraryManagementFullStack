package com.dinesh.LibraryManagementSystem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.exception.SubscriptionPlanException;
import com.dinesh.LibraryManagementSystem.mapper.SubscriptionPlanMapper;
import com.dinesh.LibraryManagementSystem.model.SubscriptionPlan;
import com.dinesh.LibraryManagementSystem.payload.dto.SubscriptionPlanDTO;
import com.dinesh.LibraryManagementSystem.repository.SubscriptionPlanRepository;
import com.dinesh.LibraryManagementSystem.service.SubscriptionPlanService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SubscriptionPlanMapper subscriptionPlanMapper;

    @Override
    public SubscriptionPlanDTO createSubscriptionPlan(
            SubscriptionPlanDTO planDTO) {

        // Check duplicate plan code
        if (subscriptionPlanRepository.existsByPlanCode(planDTO.getPlanCode())) {
            throw new SubscriptionPlanException(
                    "Subscription plan with code "
                    + planDTO.getPlanCode()
                    + " already exists");
        }

        SubscriptionPlan plan =
                subscriptionPlanMapper.toEntity(planDTO);

        SubscriptionPlan savedPlan =
                subscriptionPlanRepository.save(plan);

        return subscriptionPlanMapper.toDTO(savedPlan);
    }

    @Override
    public SubscriptionPlanDTO updateSubscriptionPlan(
            Long planId,
            SubscriptionPlanDTO planDTO) {

        SubscriptionPlan existingPlan =
                subscriptionPlanRepository.findById(planId)
                .orElseThrow(() ->
                        new SubscriptionPlanException(
                                "Subscription plan not found with id: "
                                + planId));

        // Check whether another plan already uses this plan code
        if (!existingPlan.getPlanCode().equals(planDTO.getPlanCode())
                && subscriptionPlanRepository
                        .existsByPlanCode(planDTO.getPlanCode())) {

            throw new SubscriptionPlanException(
                    "Subscription plan with code "
                    + planDTO.getPlanCode()
                    + " already exists");
        }

        existingPlan.setPlanCode(planDTO.getPlanCode());
        existingPlan.setPlanName(planDTO.getPlanName());
        existingPlan.setDescription(planDTO.getDescription());
        existingPlan.setDurationDays(planDTO.getDurationDays());
        existingPlan.setPrice(planDTO.getPrice());
        existingPlan.setCurrencyCode(planDTO.getCurrency());
        existingPlan.setMaxBookAllowed(planDTO.getMaxBookAllowed());
        existingPlan.setMaxDaysPerBook(planDTO.getMaxDaysPerBook());
        existingPlan.setDisplayOrder(planDTO.getDisplayOrder());
        existingPlan.setIsActive(planDTO.getIsActive());
        existingPlan.setIsFeatured(planDTO.getIsFeatured());
        existingPlan.setBadgeText(planDTO.getBadgeText());
        existingPlan.setAdminNotes(planDTO.getAdminNotes());
        existingPlan.setUpdatedBy(planDTO.getUpdatedBy());

        SubscriptionPlan updatedPlan =
                subscriptionPlanRepository.save(existingPlan);

        return subscriptionPlanMapper.toDTO(updatedPlan);
    }

    @Override
    public void deleteSubscriptionPlan(Long planId) {

        SubscriptionPlan plan =
                subscriptionPlanRepository.findById(planId)
                .orElseThrow(() ->
                        new SubscriptionPlanException(
                                "Subscription plan not found with id: "
                                + planId));

        subscriptionPlanRepository.delete(plan);
    }

    @Override
    public List<SubscriptionPlanDTO> getAllSubscriptionPlans() {

        List<SubscriptionPlan> plans =
                subscriptionPlanRepository
                        .findAllByOrderByDisplayOrderAsc();

        return plans.stream()
                .map(subscriptionPlanMapper::toDTO)
                .toList();
    }
}