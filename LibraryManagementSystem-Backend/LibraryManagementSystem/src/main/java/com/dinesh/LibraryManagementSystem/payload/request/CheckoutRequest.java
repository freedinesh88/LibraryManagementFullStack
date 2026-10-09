package com.dinesh.LibraryManagementSystem.payload.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.dinesh.LibraryManagementSystem.domain.BookLoanStatus;
import com.dinesh.LibraryManagementSystem.domain.BookLoanType;
import com.dinesh.LibraryManagementSystem.payload.dto.BookLoanDTO;

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
public class CheckoutRequest {

	@NotNull(message = "Book ID is mandeotry")
	private Long bookId;

	@Min(value = 1, message = "Checkout days must be at least 1")
	private Integer checkoutDays = 14;
	private String notes;

}
