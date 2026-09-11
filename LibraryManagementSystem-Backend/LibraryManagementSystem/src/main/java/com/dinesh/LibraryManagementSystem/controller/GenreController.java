package com.dinesh.LibraryManagementSystem.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dinesh.LibraryManagementSystem.service.GenreService;

@RestController
public class GenreController {
	
	private final GenreService genreService;

	public GenreController(GenreService genreService) {
		super();
		this.genreService = genreService;
	}
	

}
