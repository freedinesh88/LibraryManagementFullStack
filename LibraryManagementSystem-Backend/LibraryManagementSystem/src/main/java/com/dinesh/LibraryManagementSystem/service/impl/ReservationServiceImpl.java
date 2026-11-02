package com.dinesh.LibraryManagementSystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.domain.BookLoanStatus;
import com.dinesh.LibraryManagementSystem.domain.ReservationStatus;
import com.dinesh.LibraryManagementSystem.domain.UserRole;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.mapper.ReservationMapper;
import com.dinesh.LibraryManagementSystem.model.Book;
import com.dinesh.LibraryManagementSystem.model.Reservation;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.dto.ReservationDTO;
import com.dinesh.LibraryManagementSystem.payload.request.CheckoutRequest;
import com.dinesh.LibraryManagementSystem.payload.request.ReservationRequest;
import com.dinesh.LibraryManagementSystem.payload.request.ReservationSearchRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.repository.BookLoanRepository;
import com.dinesh.LibraryManagementSystem.repository.BookRepository;
import com.dinesh.LibraryManagementSystem.repository.ReservationRepository;
import com.dinesh.LibraryManagementSystem.service.BookLoanService;
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
	private final ReservationMapper reservationMapper;
	private final BookLoanService bookLoanService;
	int MAX_RESERVATION = 5;

	@Override
	public ReservationDTO createReservation(ReservationRequest reservationRequest) throws UserException, Exception {
		User currentUser = userService.getCurrentUser();
		return createReservationForUser(reservationRequest, currentUser.getId());
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
		if (book.getAvailableCopies() > 0) {
			throw new Exception("Book is already available");
		}
		long activeReservations = reservationRepository.countActiveReservationsByUser(userId);
		if (activeReservations >= MAX_RESERVATION) {
			throw new Exception("You have reserved " + MAX_RESERVATION + " times");
		}
		Reservation reservation = new Reservation();
		reservation.setUser(user);
		reservation.setBook(book);
		reservation.setStatus(ReservationStatus.PENDING);
		reservation.setReservedAt(LocalDateTime.now());
		reservation.setNotificationSent(false);
		reservation.setNotes(reservationRequest.getNotes());
		long pendingCount = reservationRepository.countPendingReservationsByBook(book.getId());
		reservation.setQueuePosition((int) pendingCount + 1);
		Reservation saved = reservationRepository.save(reservation);
		return reservationMapper.toDTO(reservation);
	}

	@Override
	public ReservationDTO cancelReservation(Long reservationId) throws Exception, UserException {
		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new Exception("Reservation not found with id: " + reservationId));
		User currentUser = userService.getCurrentUser();
		if (!reservation.getUser().equals(currentUser.getId()) && currentUser.getRole() != UserRole.ROLE_ADMIN) {
			throw new Exception("You can only cancel your own reservation");
		}
		if (!reservation.canBeCancelled()) {
			throw new Exception("Reservation cannot be cancelled");
		}
		reservation.setStatus(ReservationStatus.CANCELLED);
		reservation.setCancelledAt(LocalDateTime.now());
		Reservation savedReservation = reservationRepository.save(reservation);
		return reservationMapper.toDTO(savedReservation);
	}

	@Override
	public ReservationDTO fulfilReservation(Long reservationId) throws Exception, UserException {
		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new Exception("Reservation not found with id: " + reservationId));
		if (reservation.getBook().getAvailableCopies() <= 0) {
			throw new Exception("Reservation is not available for pickup");
		}
		reservation.setStatus(ReservationStatus.FULFILLED);
		reservation.setFulfilledAt(LocalDateTime.now());
		Reservation savedReservation = reservationRepository.save(reservation);
		CheckoutRequest request = new CheckoutRequest();
		request.setBookId(reservation.getBook().getId());
		request.setNotes("Assign book by admin");
		bookLoanService.checkoutBookForUser(reservation.getUser().getId(), request);
		return reservationMapper.toDTO(savedReservation);
	}

	@Override
	public PageResponse<ReservationDTO> getMyReservation(ReservationSearchRequest searchRequest) throws UserException {
		User user = userService.getCurrentUser();
		searchRequest.setUserId(user.getId());
		return searchReservation(searchRequest);
	}

	@Override
	public PageResponse<ReservationDTO> searchReservation(ReservationSearchRequest searchRequest) {
		Pageable pageable = createPageable(searchRequest);
		Page<Reservation> reservationPage = reservationRepository.searchReservationsWithFilters(
				searchRequest.getUserId(), searchRequest.getBookId(), searchRequest.getStatus(),
				searchRequest.getActiveOnly() != null ? searchRequest.getActiveOnly() : false, pageable);
		return buildPageResponse(reservationPage);
	}

	private Pageable createPageable(ReservationSearchRequest searchRequest) {

		Sort sort = "ASC".equals(searchRequest.getSortDirection()) ? Sort.by(searchRequest.getSortBy()).ascending()
				: Sort.by(searchRequest.getSortBy()).descending();

		return PageRequest.of(searchRequest.getPage(), searchRequest.getSize(), sort);
	}

	private PageResponse<ReservationDTO> buildPageResponse(Page<Reservation> reservationPage) {
		List<ReservationDTO> dtos = reservationPage.getContent().stream().map(reservationMapper::toDTO).toList();
		PageResponse<ReservationDTO> response = new PageResponse<>();
		response.setContent(dtos);
		response.setPageNumber(reservationPage.getNumber());
		response.setPageSize(reservationPage.getSize());
		response.setTotalElements(reservationPage.getTotalElements());
		response.setTotalPages(reservationPage.getTotalPages());
		response.setLast(reservationPage.isLast());
		return response;
	}

}
