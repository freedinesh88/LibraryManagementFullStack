package com.dinesh.LibraryManagementSystem.payload.request;

import java.time.LocalDateTime;

import com.dinesh.LibraryManagementSystem.domain.ReservationStatus;
import com.dinesh.LibraryManagementSystem.payload.dto.ReservationDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationSearchRequest {

	private Long userId;
	private Long bookId;
	private ReservationStatus status;
	private Boolean activeOnly;
	private int page = 0;
	private int size = 20;
	private String sortBy = "reservedAt";
	private String sortDirection = "DESC";

}
