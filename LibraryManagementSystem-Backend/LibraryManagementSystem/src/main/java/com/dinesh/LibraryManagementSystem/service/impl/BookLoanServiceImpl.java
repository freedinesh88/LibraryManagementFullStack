package com.dinesh.LibraryManagementSystem.service.impl;

import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.domain.BookLoanStatus;
import com.dinesh.LibraryManagementSystem.exception.BookException;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.model.Book;
import com.dinesh.LibraryManagementSystem.model.Subscription;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.dto.BookLoanDTO;
import com.dinesh.LibraryManagementSystem.payload.dto.SubscriptionDTO;
import com.dinesh.LibraryManagementSystem.payload.request.BookLoanSearchRequest;
import com.dinesh.LibraryManagementSystem.payload.request.CheckinRequest;
import com.dinesh.LibraryManagementSystem.payload.request.CheckoutRequest;
import com.dinesh.LibraryManagementSystem.payload.request.RenewalRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.repository.BookLoanRepository;
import com.dinesh.LibraryManagementSystem.repository.BookRepository;
import com.dinesh.LibraryManagementSystem.service.BookLoanService;
import com.dinesh.LibraryManagementSystem.service.SubscriptionService;
import com.dinesh.LibraryManagementSystem.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookLoanServiceImpl implements BookLoanService {

	private final BookLoanRepository bookLoanRepository;
	private final UserService userService;
	private final SubscriptionService subscriptionService;
	private final BookRepository bookRepository;

	@Override
	public BookLoanDTO checkoutBook(CheckoutRequest checkoutRequest) {
		return null;
	}

	@Override
	public BookLoanDTO checkoutBookForUser(Long userId, CheckoutRequest checkoutRequest)
			throws Exception, UserException {
		User user = userService.findById(userId);
		SubscriptionDTO subscription = subscriptionService.getUserActiveSubscription(user.getId());
		Book book = bookRepository.findById(checkoutRequest.getBookId())
				.orElseThrow(() -> new BookException("Book Not found with id " + checkoutRequest.getBookId()));
		if (!book.getActive()) {
			throw new BookException("Book is not active");
		}
		if (book.getAvailableCopies() <= 0) {
			throw new BookException("Book is not available");
		}
		if(bookLoanRepository.hasActiveCheckout(userId,book.getId())) {
			throw new BookException("Book already has active checkout");
		}

		return null;
	}

	@Override
	public BookLoanDTO checkinBook(CheckinRequest checkinRequest) {
		return null;
	}

	@Override
	public BookLoanDTO renewCheckout(RenewalRequest renewalRequest) {
		return null;
	}

	@Override
	public PageResponse<BookLoanDTO> getMyBookLoans(BookLoanStatus status, int page, int size) {
		return null;
	}

	@Override
	public PageResponse<BookLoanDTO> getBookLoans(BookLoanSearchRequest request) {
		return null;
	}

	@Override
	public int updateOverDueBookLoan() {
		return 0;
	}

}
