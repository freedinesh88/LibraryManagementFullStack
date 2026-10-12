package com.dinesh.LibraryManagementSystem.payload.dto;

import java.time.LocalDateTime;

import com.dinesh.LibraryManagementSystem.domain.FineStatus;
import com.dinesh.LibraryManagementSystem.domain.FineType;
import com.dinesh.LibraryManagementSystem.model.BookLoan;
import com.dinesh.LibraryManagementSystem.model.Fine;
import com.dinesh.LibraryManagementSystem.model.User;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FineDTO {

	private Long id;

	@NotNull(message = "Book Loan id is mandetory")
	private Long bookLoanId;

	private String bookTitle;

	private String bookIsbn;

	@NotNull(message = "user id is mandetory")
	private Long userId;

	private String userName;

	private String userEmail;

	@NotNull(message = "Fine type is mandetory")
	private FineType type;

	@NotNull(message = "Fine amount is mandetory")
	@PositiveOrZero(message = "Fine amount can not be negative")
	private Long amount;

	@PositiveOrZero(message = "Amount paid can not be negative")
	private Long amountPaid;

	private Long amountOutstanding;

	@NotNull(message = "Fine status is mandetory")
	private FineStatus status;

	private String reason;

	private String note;

	private Long waivedByUserId;

	private String waivedByUserName;

	private LocalDateTime waivedAt;

	private Long processedByUserId;

	private String processedByUserName;

	private String transactionId;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
