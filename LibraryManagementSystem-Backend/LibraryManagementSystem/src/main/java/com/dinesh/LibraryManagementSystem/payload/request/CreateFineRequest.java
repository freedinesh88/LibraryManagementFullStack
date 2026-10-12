package com.dinesh.LibraryManagementSystem.payload.request;

import com.dinesh.LibraryManagementSystem.domain.FineType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFineRequest {

	@NotNull(message = "Book Loan ID is mandatory")
	private Long bookLoanId;

	@NotNull(message = "Fine type is mandatory")
	private FineType type;

	private String reason;

	private String note;

	@NotNull(message = "Fine amount is mandatory")
	@PositiveOrZero(message = "Fine amount cannot be negative")
	private Long amount;
}