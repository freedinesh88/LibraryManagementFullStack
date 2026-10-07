package com.dinesh.LibraryManagementSystem.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.dinesh.LibraryManagementSystem.domain.PaymentGateway;
import com.dinesh.LibraryManagementSystem.domain.PaymentStatus;
import com.dinesh.LibraryManagementSystem.gateway.RazorpayService;
import com.dinesh.LibraryManagementSystem.mapper.PaymentMapper;
import com.dinesh.LibraryManagementSystem.model.Payment;
import com.dinesh.LibraryManagementSystem.model.Subscription;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.dto.PaymentDTO;
import com.dinesh.LibraryManagementSystem.payload.request.PaymentInitiateRequest;
import com.dinesh.LibraryManagementSystem.payload.request.PaymentVerifyRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PaymentInitiateResponse;
import com.dinesh.LibraryManagementSystem.payload.response.PaymentLinkResponse;
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
	private final RazorpayService razorpayService;
	private final PaymentMapper paymentMapper;

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

		PaymentInitiateResponse response = new PaymentInitiateResponse();
		if (req.getGateway() == PaymentGateway.RAZORPAY) {
			PaymentLinkResponse paymentLinkResponse = razorpayService.createPaymentLink(user, payment);
			response = PaymentInitiateResponse.builder().paymentId(payment.getId()).gateway(payment.getGateway())
					.checkoutUrl(paymentLinkResponse.getPayment_link_url()).transactionId(payment.getTransactionId())
					.amount(payment.getAmount()).currency(req.getCurrency()).description(payment.getDescription())
					.message("Razorpay payment link created successfully").success(true).build();
			payment.setGatewayOrderId(paymentLinkResponse.getPayment_link_id());
		}
		payment.setPaymentStatus(PaymentStatus.PROCESSING);
		paymentRepository.save(payment);
		return response;
	}

	@Override
	public PaymentDTO verifyPayment(PaymentVerifyRequest req) throws Exception {
		JSONObject paymentDetails = razorpayService.fetchPaymentDetails(req.getRazorpayPaymentId());
		JSONObject notes = paymentDetails.getJSONObject("notes");
		Long paymentId = Long.parseLong(notes.optString("payment_id"));
		Payment payment = paymentRepository.findById(paymentId).get();
		boolean isValid = razorpayService.isValidPayment(req.getRazorpayPaymentId());
		if (PaymentGateway.RAZORPAY == payment.getGateway()) {
			if (isValid) {
				payment.setGatewayOrderId(req.getRazorpayPaymentId());
			}
		}
		if (isValid) {
			payment.setPaymentStatus(PaymentStatus.SUCCESS);
			payment.setCompletedAt(LocalDateTime.now());
			payment = paymentRepository.save(payment);
		}

		return paymentMapper.toDTO(payment);
	}

	@Override
	public Page<PaymentDTO> getAllPayment(Pageable pageable) {
		Page<Payment> payment = paymentRepository.findAll(pageable);
		// TODO Auto-generated method stub
		return payment.map(paymentMapper::toDTO);
	}

}
