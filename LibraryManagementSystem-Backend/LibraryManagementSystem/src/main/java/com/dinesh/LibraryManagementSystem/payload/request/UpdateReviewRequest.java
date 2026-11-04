package com.dinesh.LibraryManagementSystem.payload.request;

import java.time.LocalDateTime;

import com.dinesh.LibraryManagementSystem.payload.dto.BookReviewDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReviewRequest {

	@NotNull
	@Min(value = 1, message = "Rating must be atleast 1")
	@Max(value = 5, message = "Rating must not exceed 5")
	private Integer rating;

	@NotBlank(message = "Review text is mandetory")
	@Size(min = 10, max = 2000, message = "Review must be between 10 and 2000 characters")
	private String reviewText;

	@Size(max = 200, message = "Review title must not exceed 200 characters")
	private String title;

}
