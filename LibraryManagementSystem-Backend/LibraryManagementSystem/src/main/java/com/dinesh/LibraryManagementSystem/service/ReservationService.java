package com.dinesh.LibraryManagementSystem.service;

import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.ReservationDTO;
import com.dinesh.LibraryManagementSystem.payload.request.ReservationRequest;
import com.dinesh.LibraryManagementSystem.payload.request.ReservationSearchRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;

public interface ReservationService {

	ReservationDTO createReservation(ReservationRequest reservationRequest);

	ReservationDTO createReservationForUser(ReservationRequest reservationRequest, Long userId) throws Exception, UserException;
	
	ReservationDTO cancelReservation(Long reservationId);
	
	ReservationDTO fulfilReservation(Long reservationId);
	
	PageResponse<ReservationDTO> getMyReservation(ReservationSearchRequest searchRequest);
	
	PageResponse<ReservationDTO> searchReservation(ReservationSearchRequest searchRequest);

}
