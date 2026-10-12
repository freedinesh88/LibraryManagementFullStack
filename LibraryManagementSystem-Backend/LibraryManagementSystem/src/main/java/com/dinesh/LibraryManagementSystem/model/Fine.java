package com.dinesh.LibraryManagementSystem.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.dinesh.LibraryManagementSystem.domain.BookLoanStatus;
import com.dinesh.LibraryManagementSystem.domain.BookLoanType;
import com.dinesh.LibraryManagementSystem.domain.FineStatus;
import com.dinesh.LibraryManagementSystem.domain.FineType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Fine {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private BookLoan bookLoan;

	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	private FineType fineType;

	@Column(nullable = false)
	private Long amount;

	private FineStatus status;

	@Column(length = 500)
	private String reason;

	@Column(length = 1000)
	private String note;

	@ManyToOne
	private User waivedBy;

	@Column(length = 500, name = "waiver_reason")
	private String waiverReason;

	@Column(name = "paid_at")
	private LocalDateTime paidAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processed_by_user_id")
	private User processedId;

	@Column(length = 100, name = "transaction_id")
	private String transactionId;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

}
