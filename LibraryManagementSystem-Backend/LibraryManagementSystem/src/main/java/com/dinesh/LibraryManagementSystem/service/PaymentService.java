package com.dinesh.LibraryManagementSystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dinesh.LibraryManagementSystem.payload.dto.PaymentDTO;
import com.dinesh.LibraryManagementSystem.payload.request.PaymentInitiateRequest;
import com.dinesh.LibraryManagementSystem.payload.request.PaymentVerifyRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PaymentInitiateResponse;

public interface PaymentService {

	PaymentInitiateResponse initiatePayment(PaymentInitiateRequest req);

	PaymentDTO verifyPayment(PaymentVerifyRequest req);

	Page<PaymentDTO> getAllPayment(Pageable pageable);
}