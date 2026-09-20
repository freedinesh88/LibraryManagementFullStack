package com.dinesh.LibraryManagementSystem.payload.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {

	private Long id;

	// User information
	@NotNull(message = "User ID is mandatory")
	private Long userId;

	private String userName;

	private String userEmail;

	// Subscription plan
	@NotNull(message = "Subscription plan ID is mandatory")
	private Long planId;

	// Plan snapshot
	private String planName;

	private String planCode;

	@PositiveOrZero(message = "Price cannot be negative")
	private Long price;

	private String currency;

	private Double priceInMajorUnits;

	// Subscription dates
	@NotNull(message = "Start date is mandatory")
	@FutureOrPresent(message = "Start date cannot be in the past")
	private LocalDate startDate;

	private LocalDate endDate;

	// Subscription status
	private Boolean isActive;

	private Boolean autoRenew;

	// Borrowing limits
	@NotNull(message = "Maximum books allowed is mandatory")
	@Positive(message = "Maximum books allowed must be positive")
	private Integer maxBookAllowed;

	@NotNull(message = "Maximum days per book is mandatory")
	@Positive(message = "Maximum days per book must be positive")
	private Integer maxDaysPerBook;

	// Cancellation
	private LocalDateTime cancelledAt;

	private String cancellationReason;

	// Additional information
	private String notes;

	// Calculated fields
	private Long daysRemaining;

	private Boolean isValid;

	private Boolean isExpired;

	// Audit fields
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}