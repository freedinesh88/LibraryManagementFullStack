package com.dinesh.LibraryManagementSystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.exception.GenreException;
import com.dinesh.LibraryManagementSystem.mapper.GenreMapper;
import com.dinesh.LibraryManagementSystem.model.Genre;
import com.dinesh.LibraryManagementSystem.payload.dto.GenreDTO;
import com.dinesh.LibraryManagementSystem.repository.GenreRepository;
import com.dinesh.LibraryManagementSystem.service.GenreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

	private final GenreRepository genreRepository;
	private final GenreMapper genreMapper;

	@Override
	public GenreDTO createGenre(GenreDTO genreDTO) {
		Genre genre = genreMapper.toEntity(genreDTO);
		Genre savedGenre = genreRepository.save(genre);
		return genreMapper.toDTO(savedGenre);
	}

	@Override
	public List<GenreDTO> getAllGenres() {
		return genreRepository.findAll().stream().map(genreMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public GenreDTO getGerneById(Long genreId) throws GenreException {
		Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new GenreException("Genre not found"));
		return genreMapper.toDTO(genre);
	}

	@Override
	public GenreDTO updateGenre(Long genreId, GenreDTO genreDTO) throws GenreException {
		Genre existingGenre = genreRepository.findById(genreId)
				.orElseThrow(() -> new GenreException("Genre not found"));
		genreMapper.updateEntityFromDTO(genreDTO, existingGenre);
		Genre updatedGenre = genreRepository.save(existingGenre);
		return genreMapper.toDTO(updatedGenre);
	}

	@Override
	public void deleteGenre(Long genreId) throws GenreException {
		Genre existingGenre = genreRepository.findById(genreId)
				.orElseThrow(() -> new GenreException("Genre not found"));
		existingGenre.setActive(false);
		genreRepository.save(existingGenre);

	}

	@Override
	public void hardDeleteGenre(Long genreId) throws GenreException {
		Genre existingGenre = genreRepository.findById(genreId)
				.orElseThrow(() -> new GenreException("Genre not found"));
		genreRepository.delete(existingGenre);

	}

	@Override
	public List<GenreDTO> getAllActiveGenreWithSubGenres() {
		List<Genre> topLevelGenres = genreRepository.findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();

		return genreMapper.toDTOList(topLevelGenres);
	}

	@Override
	public List<GenreDTO> getTopLevelGenres() {
		List<Genre> topLevelGenres = genreRepository.findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();

		return genreMapper.toDTOList(topLevelGenres);
	}

	@Override
	public long getTotalActiveGenres() {
		return genreRepository.countByActiveTrue();
	}

	@Override
	public long getBookCountByGenre(Long genreId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
