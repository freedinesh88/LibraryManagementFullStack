package com.dinesh.LibraryManagementSystem.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dinesh.LibraryManagementSystem.exception.SubscriptionException;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.mapper.SubscriptionMapper;
import com.dinesh.LibraryManagementSystem.model.Subscription;
import com.dinesh.LibraryManagementSystem.model.SubscriptionPlan;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.dto.SubscriptionDTO;
import com.dinesh.LibraryManagementSystem.repository.SubscriptionPlanRepository;
import com.dinesh.LibraryManagementSystem.repository.SubscriptionRepository;
import com.dinesh.LibraryManagementSystem.service.SubscriptionService;
import com.dinesh.LibraryManagementSystem.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

	private final SubscriptionRepository subscriptionRepository;
	private final SubscriptionMapper subscriptionMapper;
	private final UserService userService;
	private final SubscriptionPlanRepository subscriptionPlanRepository;

	@Override
	public SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO) throws UserException, Exception {

		// Get currently logged-in user
		User user = userService.getCurrentUser();

		// Find subscription plan
		SubscriptionPlan plan = subscriptionPlanRepository.findById(subscriptionDTO.getPlanId())
				.orElseThrow(() -> new Exception("Plan not found with id: " + subscriptionDTO.getPlanId()));

		// Create subscription
		Subscription subscription = subscriptionMapper.toEntity(subscriptionDTO);

		// Initialize plan details and calculate end date
		subscription.initializeFromPlan();

		// Save
		Subscription savedSubscription = subscriptionRepository.save(subscription);

		// Return response
		return subscriptionMapper.toDTO(savedSubscription);
	}

	@Override
	@Transactional(readOnly = true)
	public SubscriptionDTO getUserActiveSubscription(Long userId) throws Exception, UserException {

		if (userId == null) {
			throw new SubscriptionException("User ID is required");
		}
		User user = userService.getCurrentUser();

		Subscription subscription = subscriptionRepository.findActiveSubscriptionByUserId(userId, LocalDate.now())
				.orElseThrow(() -> new SubscriptionException("No active subscription found for user id: " + userId));

		return subscriptionMapper.toDTO(subscription);
	}

	@Override
	public SubscriptionDTO cancelSubscription(Long subscriptionId, String reason) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubscriptionDTO activeSubscription(Long subscriptionId, Long paymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SubscriptionDTO> getAllSubscription(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
