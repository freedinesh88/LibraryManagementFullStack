package com.dinesh.LibraryManagementSystem.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.LibraryManagementSystem.exception.SubscriptionException;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.SubscriptionDTO;
import com.dinesh.LibraryManagementSystem.payload.response.ApiResponse;
import com.dinesh.LibraryManagementSystem.service.SubscriptionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    // =========================
    // SUBSCRIBE
    // =========================
    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(
            @RequestBody SubscriptionDTO subscription)
            throws Exception, UserException {

        SubscriptionDTO dto = subscriptionService.subscribe(subscription);

        return ResponseEntity.ok(dto);
    }

    // =========================
    // GET ALL SUBSCRIPTIONS
    // =========================
    @GetMapping("/admin")
    public ResponseEntity<?> getAllSubscription() {

        int page = 0;
        int size = 10;

        Pageable pageable = PageRequest.of(page, size);

        List<SubscriptionDTO> dtoList =
                subscriptionService.getAllSubscription(pageable);

        return ResponseEntity.ok(dtoList);
    }

    // =========================
    // DEACTIVATE EXPIRED
    // =========================
    @GetMapping("/admin/deactivate-expired")
    public ResponseEntity<?> deactivateExpiredSubscriptions()
            throws Exception {

        subscriptionService.deactivateExpiredSubscriptions();

        ApiResponse res = new ApiResponse("task done", true);

        return ResponseEntity.ok(res);
    }

    // =========================
    // GET USER ACTIVE SUBSCRIPTION
    // =========================
    @GetMapping("/user/active")
    public ResponseEntity<?> getUserActiveSubscription(
            @RequestParam(required = false) Long userId)
            throws Exception, UserException {

        SubscriptionDTO dto =
                subscriptionService.getUserActiveSubscription(userId);

        return ResponseEntity.ok(dto);
    }

    // =========================
    // CANCEL SUBSCRIPTION
    // =========================
    @PostMapping("/cancel/{subscriptionId}")
    public ResponseEntity<?> cancelSubscription(
            @PathVariable Long subscriptionId,
            @RequestParam(required = false) String reason)
            throws Exception {

        SubscriptionDTO dto =
                subscriptionService.cancelSubscription(
                        subscriptionId, reason);

        return ResponseEntity.ok(dto);
    }

    // =========================
    // ACTIVATE SUBSCRIPTION
    // =========================
    @PostMapping("/activate/{subscriptionId}")
    public ResponseEntity<?> activeSubscription(
            @PathVariable Long subscriptionId,
            @RequestParam Long paymentId)
            throws SubscriptionException {

        SubscriptionDTO dto =
                subscriptionService.activeSubscription(
                        subscriptionId, paymentId);

        return ResponseEntity.ok(dto);
    }
}