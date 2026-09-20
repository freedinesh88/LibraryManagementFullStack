package com.dinesh.LibraryManagementSystem.service;

import java.util.List;

import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.SubscriptionPlanDTO;

public interface SubscriptionPlanService {
	
	SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO) throws UserException;
	
	SubscriptionPlanDTO updateSubscriptionPlan(Long planId, SubscriptionPlanDTO planDTO) throws UserException;
	
	void deleteSubscriptionPlan(Long planId);
	
	List<SubscriptionPlanDTO> getAllSubscriptionPlans();
	

}
