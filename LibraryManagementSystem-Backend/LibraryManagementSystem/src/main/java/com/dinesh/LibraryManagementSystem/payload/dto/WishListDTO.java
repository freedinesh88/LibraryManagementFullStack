package com.dinesh.LibraryManagementSystem.payload.dto;

import java.time.LocalDateTime;

import com.dinesh.LibraryManagementSystem.model.Book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishListDTO {

	private Long id;
	private Long userId;
	private String userFullName;
	private LocalDateTime addedAt;
	private String notes;
	private BookDTO book;
}
