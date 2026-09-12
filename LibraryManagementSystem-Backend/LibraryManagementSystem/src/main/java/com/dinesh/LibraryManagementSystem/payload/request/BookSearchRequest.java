package com.dinesh.LibraryManagementSystem.payload.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchRequest {

	private String searchTerm;
	private Long genreId;
	private Boolean availableOnly;
	private Integer page = 0;
	private Integer size = 20;
	private String sortBy = "createdAt";
	private String sortDirections = "DESC";
}
