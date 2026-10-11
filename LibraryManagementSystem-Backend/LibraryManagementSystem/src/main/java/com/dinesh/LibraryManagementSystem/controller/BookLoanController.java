package com.dinesh.LibraryManagementSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.LibraryManagementSystem.domain.BookLoanStatus;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.BookLoanDTO;
import com.dinesh.LibraryManagementSystem.payload.request.BookLoanSearchRequest;
import com.dinesh.LibraryManagementSystem.payload.request.CheckinRequest;
import com.dinesh.LibraryManagementSystem.payload.request.CheckoutRequest;
import com.dinesh.LibraryManagementSystem.payload.request.RenewalRequest;
import com.dinesh.LibraryManagementSystem.payload.response.ApiResponse;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.service.BookLoanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book-loans")
public class BookLoanController {
	private final BookLoanService bookLoanService;

	@PostMapping("/checkout")
	public ResponseEntity<?> checkoutBook(@Valid @RequestBody CheckoutRequest checkoutRequest) {
		try {
			BookLoanDTO bookLoan = bookLoanService.checkoutBook(checkoutRequest);
			return new ResponseEntity<>(bookLoan, HttpStatus.CREATED);
		} catch (Exception | UserException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), false));
		}
	}

	@PostMapping("/checkout/user/{userId}")
	public ResponseEntity<?> checkoutBookForUser(@PathVariable Long userId,
			@Valid @RequestBody CheckoutRequest checkoutRequest) throws Exception, UserException {
		BookLoanDTO bookLoan = bookLoanService.checkoutBookForUser(userId, checkoutRequest);
		return new ResponseEntity<>(bookLoan, HttpStatus.CREATED);
	}

	@PostMapping("/checkin")
	public ResponseEntity<?> checkin(@Valid @RequestBody CheckinRequest checkinRequest)
			throws Exception, UserException {
		BookLoanDTO bookLoan = bookLoanService.checkinBook(checkinRequest);
		return new ResponseEntity<>(bookLoan, HttpStatus.OK);
	}

	@PostMapping("/renew")
	public ResponseEntity<?> renew(@Valid @RequestBody RenewalRequest renewalRequest) throws Exception, UserException {
		BookLoanDTO bookLoan = bookLoanService.renewCheckout(renewalRequest);
		return new ResponseEntity<>(bookLoan, HttpStatus.OK);
	}

	@GetMapping("/my")
	public ResponseEntity<?> getMyBookLoans(@RequestParam(required = false) BookLoanStatus status,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size)
			throws UserException {
		try {
			PageResponse<BookLoanDTO> bookLoans = bookLoanService.getMyBookLoans(status, page, size);
			return ResponseEntity.ok(bookLoans);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Failed to fetch book: " + e.getMessage(), false));
		}

	}

	@PostMapping("/search")
	public ResponseEntity<?> getAllBookLoans(@RequestBody BookLoanSearchRequest searchRequest) throws Exception {
		PageResponse<BookLoanDTO> bookLoans = bookLoanService.getBookLoans(searchRequest);
		return ResponseEntity.ok(bookLoans);
	}

	@PostMapping("/admin/update-overdue")
	public ResponseEntity<?> updateOverdueBookLoans() throws Exception {
		int updateCount = bookLoanService.updateOverDueBookLoan();
		return ResponseEntity.ok(new ApiResponse("Overdue bookloans are updated", true));
	}

}
