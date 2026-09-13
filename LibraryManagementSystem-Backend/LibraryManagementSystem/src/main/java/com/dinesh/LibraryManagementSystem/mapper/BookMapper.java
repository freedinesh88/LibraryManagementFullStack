package com.dinesh.LibraryManagementSystem.mapper;

import org.springframework.stereotype.Component;

import com.dinesh.LibraryManagementSystem.exception.BookException;
import com.dinesh.LibraryManagementSystem.model.Book;
import com.dinesh.LibraryManagementSystem.model.Genre;
import com.dinesh.LibraryManagementSystem.payload.dto.BookDTO;
import com.dinesh.LibraryManagementSystem.repository.GenreRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookMapper {

	private final GenreRepository genreRepository;

	public BookDTO toDTO(Book book) {
		if (book == null) {
			return null;
		}

		BookDTO dto = BookDTO.builder().id(book.getId()).title(book.getTitle()).author(book.getAuthor())
				.isbn(book.getIsbn()).genreName(book.getGenre().getName()).genreCode(book.getGenre().getCode())
				.genreId(book.getGenre().getId()).publisher(book.getPublisher())
				.publicationDate(book.getPublicationDate()).language(book.getLanguage()).pages(book.getPages())
				.description(book.getDescription()).totalCopies(book.getTotalCopies())
				.availableCopies(book.getAvailableCopies()).price(book.getPrice())
				.coverImageUrl(book.getCoverImageUrl()).active(book.getActive()).createdAt(book.getCreatedAt())
				.updatedAt(book.getUpdatedAt()).build();

		return dto;
	}

	public Book toEntity(BookDTO dto) throws BookException {

		if (dto == null) {
			return null;
		}

		Book book = new Book();

		book.setId(dto.getId());
		book.setIsbn(dto.getIsbn());
		book.setTitle(dto.getTitle());
		book.setAuthor(dto.getAuthor());

		if (dto.getGenreId() != null) {
			Genre genre = genreRepository.findById(dto.getGenreId())
					.orElseThrow(() -> new BookException("Genre with id " + dto.getGenreId() + " not found"));

			book.setGenre(genre);
		}

		book.setPublisher(dto.getPublisher());

		book.setPublicationDate(dto.getPublicationDate());

		book.setLanguage(dto.getLanguage());
		book.setPages(dto.getPages());
		book.setDescription(dto.getDescription());
		book.setTotalCopies(dto.getTotalCopies());
		book.setAvailableCopies(dto.getAvailableCopies());
		book.setPrice(dto.getPrice());
		book.setCoverImageUrl(dto.getCoverImageUrl());
		book.setActive(true);

		return book;

	}

	public void updateEntityFromDTO(Book book, BookDTO dto) throws BookException {
		if (book == null || dto == null) {
			return;
		}
		book.setTitle(dto.getTitle());
		book.setAuthor(dto.getAuthor());

		// Update genre
		if (dto.getGenreId() != null) {
			Genre genre = genreRepository.findById(dto.getGenreId())
					.orElseThrow(() -> new BookException("Genre with id " + dto.getGenreId() + " not found"));

			book.setGenre(genre);
		}

		book.setPublisher(dto.getPublisher());
		book.setPublicationDate(dto.getPublicationDate());
		book.setLanguage(dto.getLanguage());
		book.setPages(dto.getPages());
		book.setDescription(dto.getDescription());
		book.setTotalCopies(dto.getTotalCopies());
		book.setAvailableCopies(dto.getAvailableCopies());
		book.setPrice(dto.getPrice());
		book.setCoverImageUrl(dto.getCoverImageUrl());
		if (dto.getActive() != null) {
			book.setActive(dto.getActive());
		}

	}

}
