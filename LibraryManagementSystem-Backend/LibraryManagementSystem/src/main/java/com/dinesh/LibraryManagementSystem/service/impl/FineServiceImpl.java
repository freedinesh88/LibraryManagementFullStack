package com.dinesh.LibraryManagementSystem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.domain.FineStatus;
import com.dinesh.LibraryManagementSystem.domain.FineType;
import com.dinesh.LibraryManagementSystem.payload.dto.FineDTO;
import com.dinesh.LibraryManagementSystem.payload.request.CreateFineRequest;
import com.dinesh.LibraryManagementSystem.payload.request.WaiveFineRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.payload.response.PaymentInitiateResponse;
import com.dinesh.LibraryManagementSystem.service.FineService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {

	@Override
	public FineDTO createFine(CreateFineRequest createFineRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaymentInitiateResponse payFIne(Long fineId, String transactionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void markFineAsPaid(Long fineId, Long amount, String transactionId) {
		// TODO Auto-generated method stub

	}

	@Override
	public FineDTO waiveFine(WaiveFineRequest waiveFineRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FineDTO> getMyFines(FineStatus status, FineType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResponse<FineDTO> getAllFines(FineStatus status, FineType type, Long userId, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

}
