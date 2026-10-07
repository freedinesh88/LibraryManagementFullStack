package com.dinesh.LibraryManagementSystem.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.dinesh.LibraryManagementSystem.model.Payment;
import com.dinesh.LibraryManagementSystem.payload.dto.PaymentDTO;

@Component
public class PaymentMapper {

	public PaymentDTO toDTO(Payment payment) {

		if (payment == null) {
			return null;
		}

		PaymentDTO dto = new PaymentDTO();

		dto.setId(payment.getId());

		// User details
		if (payment.getUser() != null) {
			dto.setUserId(payment.getUser().getId());
			dto.setUserName(payment.getUser().getFullName());
			dto.setUserEmail(payment.getUser().getEmail());
		}

		// Subscription details
		if (payment.getSubscription() != null) {
			dto.setSubscriptionId(payment.getSubscription().getId());
		}

		// Payment details
		dto.setPaymentType(payment.getPaymentType());
		dto.setStatus(payment.getPaymentStatus());
		dto.setGateway(payment.getGateway());
		dto.setAmount(payment.getAmount());

		// Gateway details
		dto.setTransactionId(payment.getTransactionId());
		dto.setGatewayPaymentId(payment.getGatewayPaymentId());
		dto.setGatewayOrderId(payment.getGatewayOrderId());
		dto.setGatewaySignature(payment.getGatewaySignature());

		// Other details
		dto.setDescription(payment.getDescription());
		dto.setFailureReason(payment.getFailureReason());

		// Timestamps
		dto.setInitiatedAt(payment.getInitiatedAt());
		dto.setCompletedAt(payment.getCompletedAt());
		dto.setCreatedAt(payment.getCreatedAt());
		dto.setUpdatedAt(payment.getUpdatedAt());

		return dto;
	}

	public List<PaymentDTO> toDTOList(List<Payment> payments) {

		if (payments == null) {
			return null;
		}

		return payments.stream().map(this::toDTO).collect(Collectors.toList());
	}
}