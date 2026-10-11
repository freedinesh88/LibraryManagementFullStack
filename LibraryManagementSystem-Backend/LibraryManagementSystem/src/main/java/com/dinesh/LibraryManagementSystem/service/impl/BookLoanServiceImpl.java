package com.dinesh.LibraryManagementSystem.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.domain.BookLoanStatus;
import com.dinesh.LibraryManagementSystem.domain.BookLoanType;
import com.dinesh.LibraryManagementSystem.exception.BookException;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.mapper.BookLoanMapper;
import com.dinesh.LibraryManagementSystem.model.Book;
import com.dinesh.LibraryManagementSystem.model.BookLoan;
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

import jakarta.validation.constraints.AssertFalse.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookLoanServiceImpl implements BookLoanService {

	private final BookLoanRepository bookLoanRepository;
	private final UserService userService;
	private final SubscriptionService subscriptionService;
	private final BookRepository bookRepository;
	private final BookLoanMapper bookLoanMapper;

	@Override
	public BookLoanDTO checkoutBook(CheckoutRequest checkoutRequest) throws UserException, Exception {
		User user = userService.getCurrentUser();
		return checkoutBookForUser(user.getId(), checkoutRequest);
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
		if (bookLoanRepository.hasActiveCheckout(userId, book.getId())) {
			throw new BookException("Book already has active checkout");
		}
		long activeCheckout = bookLoanRepository.countActiveBookLoansByUser(userId);
		int maxBookAllowed = subscription.getMaxBookAllowed();
		if (activeCheckout >= maxBookAllowed) {
			throw new Exception("You have reached your maximum number of books allowed");
		}
		long overdueCount = bookLoanRepository.countOverdueBookLoansByUser(userId);
		if (overdueCount > 0) {
			throw new Exception("first return old overdue book!");
		}
		BookLoan bookLoan = BookLoan.builder().user(user).book(book).type(BookLoanType.CHECKOUT)
				.status(BookLoanStatus.CHECK_OUT).checkoutDate(LocalDate.now())
				.dueDate(LocalDate.now().plusDays(checkoutRequest.getCheckoutDays())).renewalCount(0).maxRenewal(2)
				.notes(checkoutRequest.getNotes()).isOverdue(false).overdueDays(0).build();
		book.setAvailableCopies(book.getAvailableCopies() - 1);
		bookRepository.save(book);
		BookLoan savedBookLoan = bookLoanRepository.save(bookLoan);
		return bookLoanMapper.toDTO(savedBookLoan);
	}

	@Override
	public BookLoanDTO checkinBook(CheckinRequest checkinRequest) throws Exception {
		BookLoan bookLoan = bookLoanRepository.findById(checkinRequest.getBookLoanId())
				.orElseThrow(() -> new Exception("Book Loan not found"));
		if (!bookLoan.isActive()) {
			throw new BookException("Book Loan is not active");
		}
		bookLoan.setReturnDate(LocalDate.now());
		BookLoanStatus condition = checkinRequest.getCondition();
		if (condition == null) {
			condition = BookLoanStatus.RETURNED;
		}
		bookLoan.setStatus(condition);
		bookLoan.setOverdueDays(0);
		bookLoan.setIsOverdue(false);
		bookLoan.setNotes("Book returned by user.");
		if (condition != BookLoanStatus.LOST) {
			Book book = bookLoan.getBook();
			book.setAvailableCopies(book.getAvailableCopies() + 1);
			bookRepository.save(book);
		}
		BookLoan savedBookLoan = bookLoanRepository.save(bookLoan);
		return bookLoanMapper.toDTO(savedBookLoan);
	}

	@Override
	public BookLoanDTO renewCheckout(RenewalRequest renewalRequest) throws Exception {
		BookLoan bookLoan = bookLoanRepository.findById(renewalRequest.getBookLoanId())
				.orElseThrow(() -> new Exception("Book Loan not found"));
		if (!bookLoan.canRenew()) {
			throw new Exception("Book cannot renew");
		}
		bookLoan.setDueDate(bookLoan.getDueDate().plusDays(renewalRequest.getExtensionDays()));
		bookLoan.setRenewalCount(bookLoan.getRenewalCount() + 1);
		bookLoan.setNotes("Book renewed by user");
		BookLoan savedBookLoan = bookLoanRepository.save(bookLoan);
		return bookLoanMapper.toDTO(savedBookLoan);
	}

	@Override
	public PageResponse<BookLoanDTO> getMyBookLoans(BookLoanStatus status, int page, int size) throws UserException {
		User currentUser = userService.getCurrentUser();
		Page<BookLoan> bookLoanPage;
		if (status != null) {
			Pageable pageable = PageRequest.of(page, size, Sort.by("dueDate").ascending());
			bookLoanPage = bookLoanRepository.findByStatusAndUser(status, currentUser, pageable);
		} else {
			Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
			bookLoanPage = bookLoanRepository.findByUserId(currentUser.getId(), pageable);
		}
		return convertToPageResponse(bookLoanPage);
	}

	private PageResponse<BookLoanDTO> convertToPageResponse(Page<BookLoan> bookLoanPage) {
		java.util.List<BookLoanDTO> bookLoanDTOs = bookLoanPage.getContent().stream().map(bookLoanMapper::toDTO)
				.collect(Collectors.toList());

		return new PageResponse<>(bookLoanDTOs, bookLoanPage.getNumber(), bookLoanPage.getSize(),
				bookLoanPage.getTotalElements(), bookLoanPage.getTotalPages(), bookLoanPage.isLast(),
				bookLoanPage.isFirst(), bookLoanPage.isEmpty());
	}

	@Override
	public PageResponse<BookLoanDTO> getBookLoans(BookLoanSearchRequest searchRequest) {
		Pageable pageable = createPageable(searchRequest.getPage(), searchRequest.getSize(), searchRequest.getSortBy(),
				searchRequest.getSortDirection());
		Page<BookLoan> bookLoanPage;
		if (Boolean.TRUE.equals(searchRequest.getOverdueOnly())) {
			bookLoanPage = bookLoanRepository.findOverdueBookLoans(LocalDate.now(), pageable);
		} else if (searchRequest.getUserId() != null) {
			bookLoanPage = bookLoanRepository.findByUserId(searchRequest.getUserId(), pageable);
		} else if (searchRequest.getBookId() != null) {
			bookLoanPage = bookLoanRepository.findByBookId(searchRequest.getBookId(), pageable);
		} else if (searchRequest.getStatus() != null) {
			bookLoanPage = bookLoanRepository.findByStatus(searchRequest.getStatus(), pageable);
		} else if (searchRequest.getStartDate() != null && searchRequest.getEndDate() != null) {
			bookLoanPage = bookLoanRepository.findBookLoansByDateRange(searchRequest.getStartDate(),
					searchRequest.getEndDate(), pageable);
		} else {
			bookLoanPage = bookLoanRepository.findAll(pageable);
		}
		return convertToPageResponse(bookLoanPage);
	}

	@Override
	public int updateOverDueBookLoan() {
		Pageable pageable = PageRequest.of(0, 1000);
		Page<BookLoan> overduePage = bookLoanRepository.findOverdueBookLoans(LocalDate.now(), pageable);
		int updateCount = 0;
		for (BookLoan bookLoan : overduePage.getContent()) {
			if (bookLoan.getStatus() == BookLoanStatus.CHECK_OUT) {
				bookLoan.setStatus(BookLoanStatus.OVERDUE);
				bookLoan.setIsOverdue(true);
				int overdueDays = calculateOverdueDate(bookLoan.getDueDate(), LocalDate.now());
				bookLoan.setOverdueDays(overdueDays);
				bookLoanRepository.save(bookLoan);
				updateCount++;
			}
		}
		return updateCount;
	}

	private Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
		size = Math.min(size, 100);
		size = Math.max(size, 1);
		Sort sort = sortDirection.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		return PageRequest.of(page, size, sort);
	}

	public int calculateOverdueDate(LocalDate dueDate, LocalDate today) {
		if (today.isBefore(dueDate) || today.isEqual(dueDate)) {
			return 0;
		}
		return (int) ChronoUnit.DAYS.between(dueDate, today);
	}

}
