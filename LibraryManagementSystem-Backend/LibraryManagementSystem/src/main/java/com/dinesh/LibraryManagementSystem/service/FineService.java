package com.dinesh.LibraryManagementSystem.service;

import java.util.List;

import com.dinesh.LibraryManagementSystem.domain.FineStatus;
import com.dinesh.LibraryManagementSystem.domain.FineType;
import com.dinesh.LibraryManagementSystem.payload.dto.FineDTO;
import com.dinesh.LibraryManagementSystem.payload.request.CreateFineRequest;
import com.dinesh.LibraryManagementSystem.payload.request.WaiveFineRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.payload.response.PaymentInitiateResponse;

public interface FineService {

	FineDTO createFine(CreateFineRequest createFineRequest);

	PaymentInitiateResponse payFIne(Long fineId, String transactionId);

	void markFineAsPaid(Long fineId, Long amount, String transactionId);

	FineDTO waiveFine(WaiveFineRequest waiveFineRequest);

	List<FineDTO> getMyFines(FineStatus status, FineType type);

	PageResponse<FineDTO> getAllFines(FineStatus status, FineType type, Long userId, int page, int size);

}
