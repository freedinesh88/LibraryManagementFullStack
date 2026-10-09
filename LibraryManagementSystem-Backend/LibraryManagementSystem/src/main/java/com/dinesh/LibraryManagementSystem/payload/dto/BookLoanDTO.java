package com.dinesh.LibraryManagementSystem.payload.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.dinesh.LibraryManagementSystem.domain.BookLoanStatus;
import com.dinesh.LibraryManagementSystem.domain.BookLoanType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookLoanDTO {
	private Long userId;
	private Long id;
	private String userName;
	private String userEmail;
	private Long bookId;
	private String bookTitle;
	private String bookISBN;
	private String bookAuthor;
	private String bookCoverImage;
	private BookLoanType type;
	private BookLoanStatus status;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private Long remainingDays;
	private LocalDate returnDate;
	private Integer renewalCount;
	private Integer maxRenewals;
	private BigDecimal fineAmount;
	private Boolean finePaid;
	private String notes;
	private Boolean isOverDue;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
