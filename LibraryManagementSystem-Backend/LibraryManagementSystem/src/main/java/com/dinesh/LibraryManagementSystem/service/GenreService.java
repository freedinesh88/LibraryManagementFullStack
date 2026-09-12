package com.dinesh.LibraryManagementSystem.service;

import java.util.List;

import com.dinesh.LibraryManagementSystem.payload.dto.GenreDTO;

public interface GenreService {
	
	GenreDTO createGenre(GenreDTO genre);
	
	List<GenreDTO> getAllGenres();

}
