package com.dinesh.LibraryManagementSystem.payload.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.dinesh.LibraryManagementSystem.model.Book;
import com.dinesh.LibraryManagementSystem.model.Genre;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
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
public class BookDTO {

	private Long id;

	@NotBlank(message = "ISBN is mandetory")
	private String isbn;

	@NotBlank(message = "Title is mandetory")
	@Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
	private String title;

	@NotBlank(message = "Author is mandetory")
	@Size(min = 1, max = 255, message = "Author must be between 1 and 255 characters")
	private String author;

	@NotNull(message = "Genre is mandeory")
	private Long genreId;

	private String genreName;

	private String genreCode;

	@Size(max = 100, message = "Publisher name must not exceed 100 characters")
	private String publisher;

	private LocalDate publicationDate;

	@Size(max = 20, message = "Language must not exceed 20 characters")
	private String language;

	@Min(value = 1, message = "Page must be atleast 1")
	@Max(value = 5000, message = "Page must not exceed 5000")
	private Integer pages;

	@Size(max = 2000, message = "Description must not exceed 2000 characters")
	private String description;

	@Min(value = 0, message = "Total copies cannot be negative")
	@NotNull(message = "Total copies is mandeotry")
	private Integer totalCopies;

	@Min(value = 0, message = "Available copies cannot be negative")
	@NotNull(message = "Available copies is mandeotry")
	private Integer availableCopies;

	@DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
	@Digits(integer = 8, fraction = 2, message = "Price must have at most 8 integer digits and 2 decimal places")
	private BigDecimal price;

	@Size(max = 500, message = "Image URL must not exceed 500 characters")
	private String coverImageUrl;

	private Boolean alreadyHaveLoan;

	private Boolean alreadyHaveReservation;

	private Boolean active;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
