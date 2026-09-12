package com.dinesh.LibraryManagementSystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.LibraryManagementSystem.payload.dto.GenreDTO;
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
	public ResponseEntity<GenreDTO> addGenre(@RequestBody GenreDTO genre) {
		GenreDTO createdGenre = genreService.createGenre(genre);
		return ResponseEntity.ok(createdGenre);
	}
	
	@GetMapping()
	public ResponseEntity<?> getAllGenres() {
		List<GenreDTO> genres = genreService.getAllGenres();
		return ResponseEntity.ok(genres);
	}
	

}
