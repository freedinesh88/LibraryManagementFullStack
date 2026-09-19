package com.dinesh.LibraryManagementSystem.payload.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlanDTO {

    private Long id;

    @NotBlank(message = "Plan code is mandatory")
    private String planCode;

    @NotBlank(message = "Plan name is mandatory")
    private String planName;

    private String description;

    @NotNull(message = "Duration days is mandatory")
    @Positive(message = "Duration days must be positive")
    private Integer durationDays;

    @NotNull(message = "Price is mandatory")
    @PositiveOrZero(message = "Price cannot be negative")
    private Long price;

    private String currency;

    @NotNull(message = "Maximum books allowed is mandatory")
    @Positive(message = "Max book must be positive")
    private Integer maxBookAllowed;

    @NotNull(message = "Maximum days per book is mandatory")
    @Positive(message = "Max days per book must be positive")
    private Integer maxDaysPerBook;

    private Integer displayOrder;

    private Boolean isActive;

    private Boolean isFeatured;

    private String badgeText;

    private String adminNotes;
    
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;

    private String updatedBy;
}