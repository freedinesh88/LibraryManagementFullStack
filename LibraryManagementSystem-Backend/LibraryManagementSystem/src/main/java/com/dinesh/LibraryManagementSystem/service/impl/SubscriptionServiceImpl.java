package com.dinesh.LibraryManagementSystem.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
		Subscription subscription = subscriptionMapper.toEntity(subscriptionDTO, plan, user);

		// Initialize plan details and calculate end date
		subscription.initializeFromPlan();
		subscription.setIsActive(false);

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
	public SubscriptionDTO cancelSubscription(Long subscriptionId, String reason) throws Exception {

		Subscription subscription = subscriptionRepository.findById(subscriptionId)
				.orElseThrow(() -> new SubscriptionException("Subscription not found with id: " + subscriptionId));

		// Subscription must be active before cancellation
		if (!Boolean.TRUE.equals(subscription.getIsActive())) {
			throw new SubscriptionException("Subscription is already inactive");
		}

		// Cancel subscription
		subscription.setIsActive(false);
		subscription.setCancelledAt(LocalDateTime.now());

		subscription.setCancellationReason(reason != null && !reason.trim().isEmpty() ? reason : "Cancelled by user");

		// Save
		subscription = subscriptionRepository.save(subscription);

		// Return DTO
		return subscriptionMapper.toDTO(subscription);
	}

	@Override
	public SubscriptionDTO activeSubscription(Long subscriptionId, Long paymentId) throws SubscriptionException {

		Subscription subscription = subscriptionRepository.findById(subscriptionId)
				.orElseThrow(() -> new SubscriptionException("Subscription not found with id: " + subscriptionId));

//		if (paymentId == null) {
//			throw new SubscriptionException("Payment ID is required");
//		}

		if (Boolean.TRUE.equals(subscription.getIsActive())) {
			throw new SubscriptionException("Subscription is already active");
		}

		// Activate subscription
		subscription.setIsActive(true);

		Subscription savedSubscription = subscriptionRepository.save(subscription);

		return subscriptionMapper.toDTO(savedSubscription);
	}

	@Override
	public List<SubscriptionDTO> getAllSubscription(Pageable pageable) {
		List<Subscription> subscriptions = subscriptionRepository.findAll();
		return subscriptionMapper.toDTOList(subscriptions);
	}

	@Override
	public void deactivateExpiredSubscriptions() throws Exception {
		List<Subscription> expiredSubscriptions = subscriptionRepository
				.findExpiredActiveSubscriptions(LocalDate.now());
		for (Subscription subscription : expiredSubscriptions) {
			subscription.setIsActive(false);
			subscriptionRepository.save(subscription);
		}

	}

}
