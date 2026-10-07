package com.dinesh.LibraryManagementSystem.event.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.dinesh.LibraryManagementSystem.model.Payment;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

	private final ApplicationEventPublisher applicationEventPublisher;

	public void publishPaymentSuccessEvent(Payment payment) {
		applicationEventPublisher.publishEvent(payment);
	}

}
