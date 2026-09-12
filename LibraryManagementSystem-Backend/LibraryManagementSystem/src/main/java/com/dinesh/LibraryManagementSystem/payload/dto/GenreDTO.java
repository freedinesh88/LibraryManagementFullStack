package com.dinesh.LibraryManagementSystem.payload.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreDTO {
	private Long id;

	@NotBlank(message = "Genra code is mandetory")
	private String code;

	@NotBlank(message = "Genra name is mandetory")
	private String name;

	@Size(max = 500, message = "Description must not exceed 500 character")
	private String description;

	@Min(value = 0, message = "Display order cannot be negative")
	private Integer displayOrder = 0;

	private Boolean active;

	private Long parentGenreId;

	private String parentGenreName;

	private List<GenreDTO> subGenre;

	private Long bookCount;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

}
