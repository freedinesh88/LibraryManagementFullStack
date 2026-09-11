package com.dinesh.LibraryManagementSystem.service.impl;

import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.model.Genre;
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
	public Genre createGenre(Genre genre) {
		// TODO Auto-generated method stub
		return genreRepository.save(genre);
	}

}
