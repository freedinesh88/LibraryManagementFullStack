package com.dinesh.LibraryManagementSystem.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.dinesh.LibraryManagementSystem.domain.AuthProvider;
import com.dinesh.LibraryManagementSystem.domain.UserRole;

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
	private Integer durationDays;

	@Column(nullable = false)
	private Long price;

	private String currencyCode = "INR";

	@Column(nullable = false)
	@Positive(message = "Max book must be positive")
	private Integer maxBookAllowed;

	@Column(nullable = false)
	@Positive(message = "Max days per book must be positive")
	private Integer maxDaysPerBook;

	private Integer displayOrder = 0;

	private Boolean isActive = true;
	private Boolean isFeatured = false;
	private String badgeText;
	private String adminNotes;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	private String createdBy;
	private String updatedBy;

}
