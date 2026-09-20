package com.dinesh.LibraryManagementSystem.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(nullable = false)
	private SubscriptionPlan plan;

	// Snapshot of plan information at subscription time
	private String planName;

	private String planCode;

	private Long price;

	@Column(nullable = false)
	private Integer maxBookAllowed;

	@Column(nullable = false)
	private Integer maxDaysPerBook;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@Builder.Default
	@Column(nullable = false)
	private Boolean isActive = true;

	private Boolean autoRenew;

	private LocalDateTime cancelledAt;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime updatedAt;

	private String cancellationReason;

	private String notes;

	/**
	 * Checks whether the subscription is currently valid.
	 */
	public boolean isValid() {

		if (!Boolean.TRUE.equals(isActive)) {
			return false;
		}

		if (startDate == null || endDate == null) {
			return false;
		}

		LocalDate today = LocalDate.now();

		return !today.isBefore(startDate) && !today.isAfter(endDate);
	}

	/**
	 * Checks whether the subscription has expired.
	 */
	public boolean isExpired() {

		if (endDate == null) {
			return false;
		}

		return LocalDate.now().isAfter(endDate);
	}

	/**
	 * Returns the number of days remaining.
	 */
	public long getDaysRemaining() {

		if (endDate == null || isExpired()) {
			return 0;
		}

		return ChronoUnit.DAYS.between(LocalDate.now(), endDate);
	}

	/**
	 * Calculates subscription end date from the selected plan.
	 */
	public void calculateEndDate() {

		if (plan != null && plan.getDurationDays() != null && startDate != null) {

			this.endDate = startDate.plusDays(plan.getDurationDays());
		}
	}

	/**
	 * Copies the current plan details into the subscription.
	 *
	 * This creates a snapshot of the plan at the time the subscription is created.
	 */
	public void initializeFromPlan() {

		if (plan == null) {
			return;
		}

		this.planName = plan.getPlanName();
		this.planCode = plan.getPlanCode();
		this.price = plan.getPrice();
		this.maxBookAllowed = plan.getMaxBookAllowed();
		this.maxDaysPerBook = plan.getMaxDaysPerBook();

		if (startDate == null) {
			this.startDate = LocalDate.now();
		}

		calculateEndDate();
	}
}