package com.dinesh.LibraryManagementSystem.payload.request;

import com.dinesh.LibraryManagementSystem.domain.FineType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaiveFineRequest {

	@NotNull(message = "FIne ID is mandatory")
	private Long fineId;

	@NotBlank(message = "Waiver reason is mandetory")
	private String reason;

}
