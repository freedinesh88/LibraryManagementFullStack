package com.dinesh.LibraryManagementSystem.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true)
	private String planCode;

	@Column(nullable = false, length = 100)
	private String planName;

	private String description;

	@Column(nullable = false)
	@Positive(message = "Duration days must be positive")
	private Integer durationDays;

	@Column(nullable = false)
	private Long price;

	@Builder.Default
	@Column(nullable = false)
	private String currencyCode = "INR";

	@Column(nullable = false)
	@Positive(message = "Max book must be positive")
	private Integer maxBookAllowed;

	@Column(nullable = false)
	@Positive(message = "Max days per book must be positive")
	private Integer maxDaysPerBook;

	@Builder.Default
	private Integer displayOrder = 0;

	@Builder.Default
	@Column(nullable = false)
	private Boolean isActive = true;

	@Builder.Default
	@Column(nullable = false)
	private Boolean isFeatured = false;

	private String badgeText;

	private String adminNotes;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(nullable = false)
	private LocalDateTime updatedAt;

	private String createdBy;

	private String updatedBy;
}