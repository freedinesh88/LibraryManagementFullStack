package com.dinesh.LibraryManagementSystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.SubscriptionPlanDTO;
import com.dinesh.LibraryManagementSystem.payload.response.ApiResponse;
import com.dinesh.LibraryManagementSystem.service.SubscriptionPlanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscription-plans")
public class SubscriptionPlanController {

	private final SubscriptionPlanService subscriptionPlanService;

	@PostMapping("/admin/create")
	public ResponseEntity<SubscriptionPlanDTO> createSubscriptionPlan(@Valid @RequestBody SubscriptionPlanDTO planDTO)
			throws UserException {

		SubscriptionPlanDTO createdPlan = subscriptionPlanService.createSubscriptionPlan(planDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdPlan);
	}

	@PutMapping("/admin/{planId}")
	public ResponseEntity<SubscriptionPlanDTO> updateSubscriptionPlan(@PathVariable Long planId,
			@RequestBody SubscriptionPlanDTO planDTO) throws UserException {

		SubscriptionPlanDTO updatedPlan = subscriptionPlanService.updateSubscriptionPlan(planId, planDTO);

		return ResponseEntity.ok(updatedPlan);
	}

	@DeleteMapping("/admin/{planId}")
	public ResponseEntity<?> deleteSubscriptionPlan(@PathVariable Long planId) {

		subscriptionPlanService.deleteSubscriptionPlan(planId);
		ApiResponse apiResponse = new ApiResponse("PLan deleted succsessfully", true);

		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping
	public ResponseEntity<List<SubscriptionPlanDTO>> getAllSubscriptionPlans() {

		List<SubscriptionPlanDTO> plans = subscriptionPlanService.getAllSubscriptionPlans();

		return ResponseEntity.ok(plans);
	}
}