package com.dinesh.LibraryManagementSystem.service.impl;

import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.model.Genre;
import com.dinesh.LibraryManagementSystem.payload.dto.GenreDTO;
import com.dinesh.LibraryManagementSystem.repository.GenreRepository;
import com.dinesh.LibraryManagementSystem.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {
	
	private final GenreRepository genreRepository;

	public GenreServiceImpl(GenreRepository genreRepository) {
		super();
		this.genreRepository = genreRepository;
	}

	@Override
	public GenreDTO createGenre(GenreDTO genreDTO) {
		// TODO Auto-generated method stub
//		return genreRepository.save(genre);
		Genre genre = Genre.builder()
				.code(genreDTO.getCode())
				.name(genreDTO.getName())
				.description(genreDTO.getDescription())
				.displayOrder(genreDTO.getDisplayOrder())
				.active(true)
				.build();
		if (genreDTO.getParentGenreId() != null) {
		    Genre parentGenre = genreRepository.findById(genreDTO.getParentGenreId())
		            .orElseThrow(() -> new RuntimeException("Parent genre not found"));
		}
		return null;
	}

}
