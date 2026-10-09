package com.dinesh.LibraryManagementSystem.payload.request;

import java.time.LocalDate;

import com.dinesh.LibraryManagementSystem.domain.BookLoanStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookLoanSearchRequest {

	private Long userId;
	private Long bookId;
	private BookLoanStatus status;
	private Boolean overdueOnly;
	private Boolean unpaidFineOnly;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer page = 0;
	private Integer size = 20;
	private String sortBy = "createdAt";
	private String sortDirection = "DESC";

}
