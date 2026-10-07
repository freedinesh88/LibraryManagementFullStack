package com.dinesh.LibraryManagementSystem.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.dinesh.LibraryManagementSystem.exception.SubscriptionException;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.SubscriptionDTO;
import com.dinesh.LibraryManagementSystem.payload.response.PaymentInitiateResponse;

public interface SubscriptionService {

	PaymentInitiateResponse subscribe(SubscriptionDTO subscriptionDTO) throws UserException, Exception;

	SubscriptionDTO getUserActiveSubscription(Long userId) throws Exception, UserException;

	SubscriptionDTO cancelSubscription(Long subscriptionId, String reason) throws Exception;

	SubscriptionDTO activeSubscription(Long subscriptionId, Long paymentId) throws SubscriptionException;

	List<SubscriptionDTO> getAllSubscription(Pageable pageable);

	void deactivateExpiredSubscriptions() throws Exception;

}
