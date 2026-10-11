package com.dinesh.LibraryManagementSystem.mapper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.dinesh.LibraryManagementSystem.model.BookLoan;
import com.dinesh.LibraryManagementSystem.payload.dto.BookLoanDTO;

@Component
public class BookLoanMapper {
	public BookLoanDTO toDTO(BookLoan bookLoan) {
		if (bookLoan == null) {
			return null;
		}
		BookLoanDTO dto = new BookLoanDTO();
		dto.setId(bookLoan.getId());
		if (bookLoan.getUser() != null) {
			dto.setUserId(bookLoan.getUser().getId());
			dto.setUserName(bookLoan.getUser().getFullName());
			dto.setUserEmail(bookLoan.getUser().getEmail());
		}
		if (bookLoan.getBook() != null) {
			dto.setBookId(bookLoan.getBook().getId());
			dto.setBookTitle(bookLoan.getBook().getTitle());
			dto.setBookISBN(bookLoan.getBook().getIsbn());
			dto.setBookCoverImage(bookLoan.getBook().getCoverImageUrl());
		}
		dto.setType(bookLoan.getType());
		dto.setStatus(bookLoan.getStatus());
		dto.setCheckoutDate(bookLoan.getCheckoutDate());
		dto.setIsOverDue(bookLoan.getIsOverdue());
		dto.setRemainingDays(ChronoUnit.DAYS.between(LocalDate.now(), bookLoan.getDueDate()));
		dto.setReturnDate(bookLoan.getReturnDate());
		dto.setRenewalCount(bookLoan.getRenewalCount());
		dto.setMaxRenewals(bookLoan.getMaxRenewal());
		dto.setNotes(bookLoan.getNotes());
		dto.setIsOverDue(bookLoan.getIsOverdue());
		dto.setOverdueDays(bookLoan.getOverdueDays());
		dto.setCreatedAt(bookLoan.getCreatedAt());
		dto.setUpdatedAt(bookLoan.getUpdatedAt());
		return dto;
	}

}
