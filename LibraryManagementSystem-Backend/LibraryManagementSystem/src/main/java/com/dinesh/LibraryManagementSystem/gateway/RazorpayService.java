package com.dinesh.LibraryManagementSystem.gateway;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.domain.PaymentType;
import com.dinesh.LibraryManagementSystem.model.Payment;
import com.dinesh.LibraryManagementSystem.model.SubscriptionPlan;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.response.PaymentLinkResponse;
import com.dinesh.LibraryManagementSystem.service.SubscriptionPlanService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RazorpayService {

	private final SubscriptionPlanService subscriptionPlanService;

	@Value("${razorpay.key.id}")
	private String razorpayKeyId;

	@Value("${razorpay.key.secret}")
	private String razorpayKeySecret;

	@Value("${razorpay.callback.base-url:http://localhost:5173}")
	private String callbackBaseUrl;

	public PaymentLinkResponse createPaymentLink(User user, Payment payment) {

		try {

			RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

			Long amountInPaisa = payment.getAmount() * 100;

			JSONObject request = new JSONObject();

			request.put("amount", amountInPaisa);
			request.put("currency", "INR");

			request.put("description", payment.getDescription() != null ? payment.getDescription() : "Library Payment");

			JSONObject customer = new JSONObject();

			customer.put("name", user.getFullName());
			customer.put("email", user.getEmail());

			if (user.getPhone() != null) {
				customer.put("contact", user.getPhone());
			}

			request.put("customer", customer);

			JSONObject notify = new JSONObject();

			notify.put("email", true);
			notify.put("sms", user.getPhone() != null);

			request.put("notify", notify);

			request.put("reminder_enable", true);

			String successUrl = callbackBaseUrl + "/payment-success/" + payment.getId();

			String cancelUrl = callbackBaseUrl + "/payment-cancelled/" + payment.getId();

			request.put("callback_url", successUrl);
			request.put("callback_method", "get");

			JSONObject notes = new JSONObject();

			notify.put("user_id", user.getId());
			notify.put("payment_id", payment.getId());

			if (payment.getPaymentType() == PaymentType.MEMBERSHIP) {
				notes.put("subscription_id", payment.getSubscription().getId());
				notes.put("plan", payment.getSubscription().getPlan().getPlanCode());
				notes.put("type", PaymentType.MEMBERSHIP);
			} else if (payment.getPaymentType() == PaymentType.FINE) {
//            	notes.put("fine_id", payment.getf);
				notes.put("type", PaymentType.FINE);
			}
			request.put("notes", notes);

			com.razorpay.PaymentLink paymentLink = razorpayClient.paymentLink.create(request);

			String paymentLinkId = paymentLink.get("id");

			String paymentLinkUrl = paymentLink.get("short_url");

			return PaymentLinkResponse.builder().payment_link_id(paymentLinkId).payment_link_url(paymentLinkUrl)
					.build();

		} catch (RazorpayException e) {

			throw new RuntimeException("Failed to create Razorpay payment link: " + e.getMessage(), e);
		}
	}

	public JSONObject fetchPaymentDetails(String paymentId) throws Exception {
		try {
			RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
			com.razorpay.Payment payment = razorpay.payments.fetch(paymentId);
			return payment.toJson();

		} catch (RazorpayException e) {
			throw new Exception("Failed to fetch payment details:" + e.getMessage(), e);

		}
	}

	public boolean isValidPayment(String paymentId) {
		try {
			JSONObject paymentDetails = fetchPaymentDetails(paymentId);
			String status = paymentDetails.optString("status");
			long amount = paymentDetails.getLong("amount");
			long amountInRupee = amount / 100;
			JSONObject notes = paymentDetails.getJSONObject("notes");
			String paymentType = notes.optString("types");
			if (!"captured".equalsIgnoreCase(status)) {
				return false;
			}
			if (paymentType.equals(PaymentType.MEMBERSHIP.toString())) {
				String planCode = notes.optString("plan");
				SubscriptionPlan subscriptionPlan = subscriptionPlanService.getBySubscriptionPlanCode(planCode);
				return amountInRupee == subscriptionPlan.getPrice();
			} else if (paymentType.equals(PaymentType.FINE.toString())) {
				Long fineId = notes.getLong("fine_id");

			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}