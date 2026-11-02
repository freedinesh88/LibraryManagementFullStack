package com.dinesh.LibraryManagementSystem.service;

import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.ReservationDTO;
import com.dinesh.LibraryManagementSystem.payload.request.ReservationRequest;
import com.dinesh.LibraryManagementSystem.payload.request.ReservationSearchRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;

public interface ReservationService {

	ReservationDTO createReservation(ReservationRequest reservationRequest) throws UserException, Exception;

	ReservationDTO createReservationForUser(ReservationRequest reservationRequest, Long userId) throws Exception, UserException;
	
	ReservationDTO cancelReservation(Long reservationId) throws Exception, UserException;
	
	ReservationDTO fulfilReservation(Long reservationId) throws Exception, UserException;
	
	PageResponse<ReservationDTO> getMyReservation(ReservationSearchRequest searchRequest) throws UserException;
	
	PageResponse<ReservationDTO> searchReservation(ReservationSearchRequest searchRequest);

}
