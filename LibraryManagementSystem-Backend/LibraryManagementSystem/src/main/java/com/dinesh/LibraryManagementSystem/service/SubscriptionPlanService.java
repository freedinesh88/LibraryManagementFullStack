package com.dinesh.LibraryManagementSystem.service;

import java.util.List;

import com.dinesh.LibraryManagementSystem.payload.dto.SubscriptionPlanDTO;

public interface SubscriptionPlanService {
	
	SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO);
	
	SubscriptionPlanDTO updateSubscriptionPlan(Long planId, SubscriptionPlanDTO planDTO);
	
	void deleteSubscriptionPlan(Long planId);
	
	List<SubscriptionPlanDTO> getAllSubscriptionPlans();
	

}
