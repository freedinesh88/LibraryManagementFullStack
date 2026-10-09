package com.dinesh.LibraryManagementSystem.payload.request;

import com.dinesh.LibraryManagementSystem.domain.BookLoanStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckinRequest {

	@NotNull(message = "Book Loan ID is mandeotry")
	private Long bookLoanId;

	private BookLoanStatus condition = BookLoanStatus.RETURNED;

	private String notes;

}
