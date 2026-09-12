package com.dinesh.LibraryManagementSystem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dinesh.LibraryManagementSystem.exception.GenreException;
import com.dinesh.LibraryManagementSystem.payload.dto.GenreDTO;

public interface GenreService {
	
	GenreDTO createGenre(GenreDTO genre);
	
	List<GenreDTO> getAllGenres();
	
	GenreDTO getGerneById(Long genreId) throws GenreException;
	
	GenreDTO updateGenre(Long genreId, GenreDTO genre) throws GenreException;
	
	void deleteGenre(Long genreId) throws GenreException;
	
	void hardDeleteGenre(Long genreId) throws GenreException;
	
	List<GenreDTO> getAllActiveGenreWithSubGenres();
	
	List<GenreDTO> getTopLevelGenres();
	
//	Page<GenreDTO> searchGenres(String searchTerm, Pageable pageable);
	
	Long getTotalActiveGenres();
	
	Long getBookCountByGenre(Long genreId);

}
