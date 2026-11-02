package com.dinesh.LibraryManagementSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.LibraryManagementSystem.domain.ReservationStatus;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.ReservationDTO;
import com.dinesh.LibraryManagementSystem.payload.request.ReservationRequest;
import com.dinesh.LibraryManagementSystem.payload.request.ReservationSearchRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping
	public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationRequest reservationRequest)
			throws Exception, UserException {
		ReservationDTO reservationDTO = reservationService.createReservation(reservationRequest);
		return ResponseEntity.ok(reservationDTO);
	}

	@PostMapping("/user/{userId}")
	public ResponseEntity<?> createReservationForUser(@PathVariable Long userId,
			@Valid @RequestBody ReservationRequest reservationRequest) throws Exception, UserException {
		ReservationDTO reservationDTO = reservationService.createReservationForUser(reservationRequest, userId);
		return new ResponseEntity<>(reservationDTO, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> cancelReservation(@PathVariable Long id) throws Exception, UserException {
		ReservationDTO reservationDTO = reservationService.cancelReservation(id);
		return ResponseEntity.ok(reservationDTO);
	}

	@PostMapping("/{id}/fulfill")
	public ResponseEntity<?> fulfillReservation(@PathVariable Long id) throws Exception, UserException {
		ReservationDTO reservationDTO = reservationService.fulfilReservation(id);
		return ResponseEntity.ok(reservationDTO);
	}

	@GetMapping("/my")
	public ResponseEntity<PageResponse<ReservationDTO>> getMyReservations(
			@RequestParam(required = false) ReservationStatus status,
			@RequestParam(required = false) Boolean activeOnly, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "reservedAt") String sortBy,
			@RequestParam(defaultValue = "DESC") String sortDirection) throws UserException {
		ReservationSearchRequest searchRequest = new ReservationSearchRequest();
		searchRequest.setStatus(status);
		searchRequest.setActiveOnly(activeOnly);
		searchRequest.setPage(page);
		searchRequest.setSize(size);
		searchRequest.setSortBy(sortBy);
		searchRequest.setSortDirection(sortDirection);
		PageResponse<ReservationDTO> reservations = reservationService.getMyReservation(searchRequest);
		return ResponseEntity.ok(reservations);
	}

	@GetMapping
	public ResponseEntity<PageResponse<ReservationDTO>> searchReservations(@RequestParam(required = false) Long userId,
			@RequestParam(required = false) Long bookId, @RequestParam(required = false) ReservationStatus status,
			@RequestParam(required = false) Boolean activeOnly, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "reservedAt") String sortBy,
			@RequestParam(defaultValue = "DESC") String sortDirection) {
		ReservationSearchRequest searchRequest = new ReservationSearchRequest();
		searchRequest.setUserId(userId);
		searchRequest.setBookId(bookId);
		searchRequest.setStatus(status);
		searchRequest.setActiveOnly(activeOnly);
		searchRequest.setPage(page);
		searchRequest.setSize(size);
		searchRequest.setSortBy(sortBy);
		searchRequest.setSortDirection(sortDirection);
		PageResponse<ReservationDTO> reservations = reservationService.searchReservation(searchRequest);
		return ResponseEntity.ok(reservations);

	}

}
