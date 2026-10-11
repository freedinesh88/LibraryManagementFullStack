package com.dinesh.LibraryManagementSystem.service;

import com.dinesh.LibraryManagementSystem.domain.BookLoanStatus;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.BookLoanDTO;
import com.dinesh.LibraryManagementSystem.payload.request.BookLoanSearchRequest;
import com.dinesh.LibraryManagementSystem.payload.request.CheckinRequest;
import com.dinesh.LibraryManagementSystem.payload.request.CheckoutRequest;
import com.dinesh.LibraryManagementSystem.payload.request.RenewalRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;

public interface BookLoanService {

	BookLoanDTO checkoutBook(CheckoutRequest checkoutRequest) throws UserException, Exception;

	BookLoanDTO checkoutBookForUser(Long userId, CheckoutRequest checkoutRequest) throws Exception, UserException;

	BookLoanDTO checkinBook(CheckinRequest checkinRequest) throws Exception;

	BookLoanDTO renewCheckout(RenewalRequest renewalRequest) throws Exception;

	PageResponse<BookLoanDTO> getMyBookLoans(BookLoanStatus status, int page, int size) throws UserException;

	PageResponse<BookLoanDTO> getBookLoans(BookLoanSearchRequest request);

	int updateOverDueBookLoan();

}
