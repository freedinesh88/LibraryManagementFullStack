package com.dinesh.LibraryManagementSystem.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.dinesh.LibraryManagementSystem.domain.PaymentGateway;
import com.dinesh.LibraryManagementSystem.domain.PaymentStatus;
import com.dinesh.LibraryManagementSystem.domain.PaymentType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Subscription subscription;

	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	@Enumerated(EnumType.STRING)
	private PaymentGateway gateway;

	private Long amount;

	private String transactionId;

	private String gatewayPaymentId;

	private String gatewayOrderId;

	private String gatewaySignature;

	private String description;

	private String failureReason;

	@CreationTimestamp
	private LocalDateTime initiatedAt;

	private LocalDateTime completedAt;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}