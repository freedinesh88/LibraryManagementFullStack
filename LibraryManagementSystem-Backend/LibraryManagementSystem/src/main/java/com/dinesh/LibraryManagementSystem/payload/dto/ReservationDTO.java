package com.dinesh.LibraryManagementSystem.payload.dto;

import java.time.LocalDateTime;

import com.dinesh.LibraryManagementSystem.domain.ReservationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {
	private Long id;
	private Long userId;
	private String userName;
	private String userEmail;
	private Long bookId;
	private String bookTitle;
	private String bookIsbn;
	private String bookAuthor;
	private Boolean isBookAvailable;
	private boolean isExpired;
	private ReservationStatus status;
	private LocalDateTime reservedAt;
	private LocalDateTime availableAt;
	private LocalDateTime availableUntil;
	private LocalDateTime fulfilledAt;
	private LocalDateTime cancelledAt;
	private Integer queuePosition;
	private Boolean notificationSent;
	private String notes;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
	private boolean canBeCancelled;
	private long hoursUntilExpiry;
}
