package com.dinesh.LibraryManagementSystem.service.impl;

import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.domain.BookLoanStatus;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.model.Book;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.dto.ReservationDTO;
import com.dinesh.LibraryManagementSystem.payload.request.ReservationRequest;
import com.dinesh.LibraryManagementSystem.payload.request.ReservationSearchRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.repository.BookLoanRepository;
import com.dinesh.LibraryManagementSystem.repository.BookRepository;
import com.dinesh.LibraryManagementSystem.repository.ReservationRepository;
import com.dinesh.LibraryManagementSystem.service.ReservationService;
import com.dinesh.LibraryManagementSystem.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final BookLoanRepository bookLoanRepository;
	private final UserService userService;
	private final BookRepository bookRepository;
	private final ReservationRepository reservationRepository;

	@Override
	public ReservationDTO createReservation(ReservationRequest reservationRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservationDTO createReservationForUser(ReservationRequest reservationRequest, Long userId)
			throws Exception, UserException {
		boolean alreadyHasLoan = bookLoanRepository.existByUserIdAndBookIdAndStatus(userId,
				reservationRequest.getBookId(), BookLoanStatus.CHECK_OUT);
		if (alreadyHasLoan) {
			throw new Exception("You already have loan on this book");
		}
		User user = userService.getCurrentUser();
		Book book = bookRepository.findById(reservationRequest.getBookId())
				.orElseThrow(() -> new Exception("Book not found"));
		if (reservationRepository.hasActiveReservation(userId, book.getId())) {
			throw new Exception("You already have reservation on this book");
		}
		return null;
	}

	@Override
	public ReservationDTO cancelReservation(Long reservationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservationDTO fulfilReservation(Long reservationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResponse<ReservationDTO> getMyReservation(ReservationSearchRequest searchRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResponse<ReservationDTO> searchReservation(ReservationSearchRequest searchRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
