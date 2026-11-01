package com.dinesh.LibraryManagementSystem.payload.request;

import java.time.LocalDateTime;

import com.dinesh.LibraryManagementSystem.domain.ReservationStatus;
import com.dinesh.LibraryManagementSystem.payload.dto.ReservationDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {

	@NotNull(message = "Book ID is mandetory")
	private Long bookId;

	private String notes;

}
