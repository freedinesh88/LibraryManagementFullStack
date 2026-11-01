package com.dinesh.LibraryManagementSystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.LibraryManagementSystem.domain.FineStatus;
import com.dinesh.LibraryManagementSystem.domain.FineType;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.FineDTO;
import com.dinesh.LibraryManagementSystem.payload.request.CreateFineRequest;
import com.dinesh.LibraryManagementSystem.payload.request.WaiveFineRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.payload.response.PaymentInitiateResponse;
import com.dinesh.LibraryManagementSystem.service.FineService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fines")
public class FineController {

	private final FineService fineService;

	@PostMapping
	public ResponseEntity<?> createFine(@Valid @RequestBody CreateFineRequest fineRequest) throws Exception {
		FineDTO fineDTO = fineService.createFine(fineRequest);
		return ResponseEntity.ok(fineDTO);
	}

	@PostMapping("/{id}/pay")
	public ResponseEntity<?> payFine(@PathVariable Long id, @RequestParam(required = false) String transactionId)
			throws Exception, UserException {
		PaymentInitiateResponse res = fineService.payFIne(id, transactionId);
		return ResponseEntity.ok(res);
	}

	@PostMapping("/waive")
	public ResponseEntity<?> waiveFine(@Valid @RequestBody WaiveFineRequest waiveFineRequest)
			throws Exception, UserException {
		FineDTO fineDTO = fineService.waiveFine(waiveFineRequest);
		return ResponseEntity.ok(fineDTO);
	}

	@GetMapping("/my")
	public ResponseEntity<?> getMyFine(@RequestParam(required = false) FineStatus status,
			@RequestParam(required = false) FineType type) throws UserException {
		List<FineDTO> fines = fineService.getMyFines(status, type);
		return ResponseEntity.ok(fines);
	}

	@GetMapping
	public ResponseEntity<?> getAllFines(@RequestParam(required = false) FineStatus status,
			@RequestParam(required = false) FineType type, @RequestParam(required = false) Long userId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
		PageResponse<FineDTO> fines = fineService.getAllFines(status, type, userId, page, size);
		return ResponseEntity.ok(fines);

	}

}
