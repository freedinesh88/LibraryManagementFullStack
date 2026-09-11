package com.dinesh.LibraryManagementSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.LibraryManagementSystem.model.Genre;
import com.dinesh.LibraryManagementSystem.service.GenreService;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
	
	private final GenreService genreService;

	public GenreController(GenreService genreService) {
		super();
		this.genreService = genreService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<Genre> addGenre(@RequestBody Genre genre) {
		Genre createdGenre = genreService.createGenre(genre);
		return ResponseEntity.ok(createdGenre);
	}
	

}
