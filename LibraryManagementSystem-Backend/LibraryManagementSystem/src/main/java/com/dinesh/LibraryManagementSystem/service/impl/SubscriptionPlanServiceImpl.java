package com.dinesh.LibraryManagementSystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.exception.SubscriptionPlanException;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.mapper.SubscriptionPlanMapper;
import com.dinesh.LibraryManagementSystem.model.SubscriptionPlan;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.dto.SubscriptionPlanDTO;
import com.dinesh.LibraryManagementSystem.repository.SubscriptionPlanRepository;
import com.dinesh.LibraryManagementSystem.service.SubscriptionPlanService;
import com.dinesh.LibraryManagementSystem.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

	private final SubscriptionPlanRepository subscriptionPlanRepository;
	private final SubscriptionPlanMapper subscriptionPlanMapper;
	private final UserService userService;

	@Override
	public SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO) throws UserException {

		// Check duplicate plan code
		if (subscriptionPlanRepository.existsByPlanCode(planDTO.getPlanCode())) {

			throw new SubscriptionPlanException(
					"Subscription plan with code " + planDTO.getPlanCode() + " already exists");
		}

		SubscriptionPlan plan = subscriptionPlanMapper.toEntity(planDTO);

		User currentUser = userService.getCurrentUser();

		plan.setCreatedBy(currentUser.getFullName());
		plan.setUpdatedBy(currentUser.getFullName());

		SubscriptionPlan savedPlan = subscriptionPlanRepository.save(plan);

		return subscriptionPlanMapper.toDTO(savedPlan);
	}

	@Override
	public SubscriptionPlanDTO updateSubscriptionPlan(Long planId, SubscriptionPlanDTO planDTO) throws UserException {

		SubscriptionPlan existingPlan = subscriptionPlanRepository.findById(planId)
				.orElseThrow(() -> new SubscriptionPlanException("Subscription plan not found with id: " + planId));

		// Check duplicate plan code only if a new code was supplied
		if (planDTO.getPlanCode() != null && !existingPlan.getPlanCode().equals(planDTO.getPlanCode())
				&& subscriptionPlanRepository.existsByPlanCode(planDTO.getPlanCode())) {

			throw new SubscriptionPlanException(
					"Subscription plan with code " + planDTO.getPlanCode() + " already exists");
		}

		// Update only non-null fields
		subscriptionPlanMapper.updateEntity(existingPlan, planDTO);

		User currentUser = userService.getCurrentUser();

		// Do NOT change createdBy
		existingPlan.setUpdatedBy(currentUser.getFullName());

		SubscriptionPlan updatedPlan = subscriptionPlanRepository.save(existingPlan);

		return subscriptionPlanMapper.toDTO(updatedPlan);
	}

	@Override
	public void deleteSubscriptionPlan(Long planId) {

		SubscriptionPlan plan = subscriptionPlanRepository.findById(planId)
				.orElseThrow(() -> new SubscriptionPlanException("Subscription plan not found with id: " + planId));

		subscriptionPlanRepository.delete(plan);
	}

	@Override
	public List<SubscriptionPlanDTO> getAllSubscriptionPlans() {

		List<SubscriptionPlan> plans = subscriptionPlanRepository.findAll();

		return plans.stream().map(subscriptionPlanMapper::toDTO).collect(Collectors.toList());
	}
}