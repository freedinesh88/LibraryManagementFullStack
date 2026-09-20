package com.dinesh.LibraryManagementSystem.mapper;

import org.springframework.stereotype.Component;

import com.dinesh.LibraryManagementSystem.model.SubscriptionPlan;
import com.dinesh.LibraryManagementSystem.payload.dto.SubscriptionPlanDTO;

@Component
public class SubscriptionPlanMapper {

	// Entity -> DTO
	public SubscriptionPlanDTO toDTO(SubscriptionPlan plan) {

		if (plan == null) {
			return null;
		}

		return SubscriptionPlanDTO.builder().id(plan.getId()).planCode(plan.getPlanCode()).planName(plan.getPlanName())
				.description(plan.getDescription()).durationDays(plan.getDurationDays()).price(plan.getPrice())

				// Entity currencyCode -> DTO currency
				.currency(plan.getCurrencyCode())

				.maxBookAllowed(plan.getMaxBookAllowed()).maxDaysPerBook(plan.getMaxDaysPerBook())
				.displayOrder(plan.getDisplayOrder()).isActive(plan.getIsActive()).isFeatured(plan.getIsFeatured())
				.badgeText(plan.getBadgeText()).adminNotes(plan.getAdminNotes()).createdAt(plan.getCreatedAt())
				.updatedAt(plan.getUpdatedAt()).createdBy(plan.getCreatedBy()).updatedBy(plan.getUpdatedBy()).build();
	}

	// DTO -> Entity
	public SubscriptionPlan toEntity(SubscriptionPlanDTO dto) {

		if (dto == null) {
			return null;
		}

		return SubscriptionPlan.builder().id(dto.getId()).planCode(dto.getPlanCode()).planName(dto.getPlanName())
				.description(dto.getDescription()).durationDays(dto.getDurationDays()).price(dto.getPrice())

				// DTO currency -> Entity currencyCode
				.currencyCode(dto.getCurrency())

				.maxBookAllowed(dto.getMaxBookAllowed()).maxDaysPerBook(dto.getMaxDaysPerBook())
				.displayOrder(dto.getDisplayOrder()).isActive(dto.getIsActive()).isFeatured(dto.getIsFeatured())
				.badgeText(dto.getBadgeText()).adminNotes(dto.getAdminNotes()).createdAt(dto.getCreatedAt())
				.updatedAt(dto.getUpdatedAt()).createdBy(dto.getCreatedBy()).updatedBy(dto.getUpdatedBy()).build();
	}

	// Update existing entity
	public void updateEntity(SubscriptionPlan plan, SubscriptionPlanDTO dto) {

		if (dto.getPlanCode() != null) {
			plan.setPlanCode(dto.getPlanCode());
		}

		if (dto.getPlanName() != null) {
			plan.setPlanName(dto.getPlanName());
		}

		if (dto.getDescription() != null) {
			plan.setDescription(dto.getDescription());
		}

		if (dto.getDurationDays() != null) {
			plan.setDurationDays(dto.getDurationDays());
		}

		if (dto.getPrice() != null) {
			plan.setPrice(dto.getPrice());
		}

		if (dto.getCurrency() != null) {
			plan.setCurrencyCode(dto.getCurrency());
		}

		if (dto.getMaxBookAllowed() != null) {
			plan.setMaxBookAllowed(dto.getMaxBookAllowed());
		}

		if (dto.getMaxDaysPerBook() != null) {
			plan.setMaxDaysPerBook(dto.getMaxDaysPerBook());
		}

		if (dto.getDisplayOrder() != null) {
			plan.setDisplayOrder(dto.getDisplayOrder());
		}

		if (dto.getIsActive() != null) {
			plan.setIsActive(dto.getIsActive());
		}

		if (dto.getIsFeatured() != null) {
			plan.setIsFeatured(dto.getIsFeatured());
		}

		if (dto.getBadgeText() != null) {
			plan.setBadgeText(dto.getBadgeText());
		}

		if (dto.getAdminNotes() != null) {
			plan.setAdminNotes(dto.getAdminNotes());
		}

		if (dto.getUpdatedBy() != null) {
			plan.setUpdatedBy(dto.getUpdatedBy());
		}
	}
}