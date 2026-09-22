package com.dinesh.LibraryManagementSystem.payload.dto;

import java.time.LocalDateTime;

import com.dinesh.LibraryManagementSystem.domain.PaymentGateway;
import com.dinesh.LibraryManagementSystem.domain.PaymentStatus;
import com.dinesh.LibraryManagementSystem.domain.PaymentType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

	private Long id;

	@NotNull(message = "User ID is mandatory")
	private Long userId;

	private String userName;

	private String userEmail;

	private Long bookLoanId;

	private Long subscriptionId;

	@NotNull(message = "Payment type is mandatory")
	private PaymentType paymentType;

	private PaymentStatus status;

	@NotNull(message = "Payment gateway is mandatory")
	private PaymentGateway gateway;

	@NotNull(message = "Amount is mandatory")
	@Positive(message = "Amount must be positive")
	private Long amount;

	@Size(min = 3, max = 3, message = "Currency must be a 3-letter code")
	private String currency;

	private String transactionId;

	private String gatewayPaymentId;

	private String gatewayOrderId;

	private String gatewaySignature;

	private String description;

	private String failureReason;

	private Integer retryCount;

	private LocalDateTime initiatedAt;

	private LocalDateTime completedAt;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}