package com.dinesh.LibraryManagementSystem.payload.request;

import com.dinesh.LibraryManagementSystem.domain.BookLoanStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RenewalRequest {
	@NotNull(message = "Book Loan ID is mandeotry")
	private Long bookLoanId;

	@Min(value = 1, message = "Extension days must be at least 1")
	private Integer extensionDays = 14;
	private String notes;

}
