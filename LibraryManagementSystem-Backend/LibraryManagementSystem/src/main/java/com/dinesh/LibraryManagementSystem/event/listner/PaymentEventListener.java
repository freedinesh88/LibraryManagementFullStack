package com.dinesh.LibraryManagementSystem.event.listner;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.dinesh.LibraryManagementSystem.exception.SubscriptionException;
import com.dinesh.LibraryManagementSystem.model.Payment;
import com.dinesh.LibraryManagementSystem.service.SubscriptionService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

	private final SubscriptionService subscriptionService;

	@Async
	@EventListener
	@Transactional
	public void handlePaymentSuccess(Payment payment) throws SubscriptionException {
		switch (payment.getPaymentType()) {
		case FINE:
		case LOST_BOOK_PENALTY:
		case DAMAGED_BOOK_PENALTY:
			break;
		case MEMBERSHIP:
			subscriptionService.activeSubscription(payment.getSubscription().getId(), payment.getId());
		}

	}
}