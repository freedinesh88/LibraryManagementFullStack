package com.dinesh.LibraryManagementSystem.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.SubscriptionDTO;

public interface SubscriptionService {

	SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO) throws UserException, Exception;

	SubscriptionDTO getUserActiveSubscription(Long userId) throws Exception, UserException;

	SubscriptionDTO cancelSubscription(Long subscriptionId, String reason);

	SubscriptionDTO activeSubscription(Long subscriptionId, Long paymentId);

	List<SubscriptionDTO> getAllSubscription(Pageable pageable);

}
