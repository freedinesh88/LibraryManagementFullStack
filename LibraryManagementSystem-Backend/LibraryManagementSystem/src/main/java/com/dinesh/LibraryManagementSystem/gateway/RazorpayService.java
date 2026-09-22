package com.dinesh.LibraryManagementSystem.gateway;

import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.model.Payment;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.response.PaymentLinkResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RazorpayService {

	public PaymentLinkResponse createPaymentLink(User user, Payment payment) {

		return null;

	}

}
