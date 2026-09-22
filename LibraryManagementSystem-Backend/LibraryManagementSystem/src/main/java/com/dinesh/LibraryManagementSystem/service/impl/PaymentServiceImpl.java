package com.dinesh.LibraryManagementSystem.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.domain.PaymentStatus;
import com.dinesh.LibraryManagementSystem.model.Payment;
import com.dinesh.LibraryManagementSystem.model.Subscription;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.dto.PaymentDTO;
import com.dinesh.LibraryManagementSystem.payload.request.PaymentInitiateRequest;
import com.dinesh.LibraryManagementSystem.payload.request.PaymentVerifyRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PaymentInitiateResponse;
import com.dinesh.LibraryManagementSystem.repository.PaymentRepository;
import com.dinesh.LibraryManagementSystem.repository.SubscriptionRepository;
import com.dinesh.LibraryManagementSystem.repository.UserRepository;
import com.dinesh.LibraryManagementSystem.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final UserRepository userRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final PaymentRepository paymentRepository;

	@Override
	public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest req) {
		User user = userRepository.findById(req.getUserId())
				.orElseThrow(() -> new RuntimeException("User not found with id: " + req.getUserId()));
		Payment payment = new Payment();
		payment.setUser(user);
		payment.setPaymentType(req.getPaymentType());
		payment.setGateway(req.getGateway());
		payment.setAmount(req.getAmount());
		payment.setDescription(req.getDescription());

		payment.setPaymentStatus(PaymentStatus.PENDING);
		payment.setTransactionId("TXN_" + UUID.randomUUID());

		payment.setInitiatedAt(LocalDateTime.now());

		if (req.getSubscriptionId() != null) {
			Subscription subscription = subscriptionRepository.findById(req.getSubscriptionId()).orElseThrow(
					() -> new RuntimeException("Subscription not found with id: " + req.getSubscriptionId()));
			payment.setSubscription(subscription);
		}
		payment = paymentRepository.save(payment);
		return null;
	}

	@Override
	public PaymentDTO verifyPayment(PaymentVerifyRequest req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PaymentDTO> getAllPayment(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
