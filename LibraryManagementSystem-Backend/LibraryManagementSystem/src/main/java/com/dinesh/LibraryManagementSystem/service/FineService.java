package com.dinesh.LibraryManagementSystem.service;

import java.util.List;

import com.dinesh.LibraryManagementSystem.domain.FineStatus;
import com.dinesh.LibraryManagementSystem.domain.FineType;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.FineDTO;
import com.dinesh.LibraryManagementSystem.payload.request.CreateFineRequest;
import com.dinesh.LibraryManagementSystem.payload.request.WaiveFineRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.payload.response.PaymentInitiateResponse;

public interface FineService {

	FineDTO createFine(CreateFineRequest createFineRequest) throws Exception;

	PaymentInitiateResponse payFIne(Long fineId, String transactionId) throws Exception, UserException;

	void markFineAsPaid(Long fineId, Long amount, String transactionId) throws Exception;

	FineDTO waiveFine(WaiveFineRequest waiveFineRequest) throws Exception, UserException;

	List<FineDTO> getMyFines(FineStatus status, FineType type) throws UserException;

	PageResponse<FineDTO> getAllFines(FineStatus status, FineType type, Long userId, int page, int size);

}
