package com.dinesh.LibraryManagementSystem.mapper;

import java.util.stream.Collectors;

import com.dinesh.LibraryManagementSystem.model.Genre;
import com.dinesh.LibraryManagementSystem.payload.dto.GenreDTO;

public class GenreMapper {

	public static GenreDTO toDTO(Genre savedGenre) {
		if (savedGenre == null) {
			return null;
		}
		GenreDTO dto = GenreDTO.builder().id(savedGenre.getId()).code(savedGenre.getCode()).name(savedGenre.getName())
				.description(savedGenre.getDescription()).displayOrder(savedGenre.getDisplayOrder())
				.active(savedGenre.getActive()).createdAt(savedGenre.getCreatedAt())
				.updatedAt(savedGenre.getUpdatedAt()).build();
		if (savedGenre.getParentGenre() != null) {
			dto.setParentGenreId(savedGenre.getParentGenre().getId());
			dto.setParentGenreName(savedGenre.getParentGenre().getName());
		}
		if (savedGenre.getSubGenres() != null && !savedGenre.getSubGenres().isEmpty()) {
			dto.setSubGenre(savedGenre.getSubGenres().stream().filter(subGenre -> subGenre.getActive())
					.map(subGenre -> toDTO(subGenre)).collect(Collectors.toList()));
		}
		return dto;
	}

}
