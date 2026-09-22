package com.dinesh.LibraryManagementSystem.payload.request;

import com.dinesh.LibraryManagementSystem.domain.PaymentGateway;
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
public class PaymentInitiateRequest {

	@NotNull(message = "User ID is mandatory")
	private Long userId;

	private Long bookLoanId;

	@NotNull(message = "Payment type is mandatory")
	private PaymentType paymentType;

	@NotNull(message = "Payment gateway is mandatory")
	private PaymentGateway gateway;

	@NotNull(message = "Amount is mandatory")
	@Positive(message = "Amount must be positive")
	private Long amount;

	@Size(min = 3, max = 3, message = "Currency must be a 3-letter code")
	private String currency = "INR";

	@Size(max = 500, message = "Description must not exceed 500 characters")
	private String description;

	private Long fineId;

	private Long subscriptionId;

	@Size(max = 500, message = "Success URL must not exceed 500 characters")
	private String successUrl;

	@Size(max = 500, message = "Cancel URL must not exceed 500 characters")
	private String cancelUrl;
}