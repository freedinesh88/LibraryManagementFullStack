package com.dinesh.LibraryManagementSystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import com.dinesh.LibraryManagementSystem.exception.BookException;
import com.dinesh.LibraryManagementSystem.mapper.BookMapper;
import com.dinesh.LibraryManagementSystem.model.Book;
import com.dinesh.LibraryManagementSystem.payload.dto.BookDTO;
import com.dinesh.LibraryManagementSystem.payload.request.BookSearchRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.repository.BookRepository;
import com.dinesh.LibraryManagementSystem.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;
	private final BookMapper bookMapper;

	@Override
	public BookDTO createBook(BookDTO bookDTO) throws BookException {
		if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
			throw new BookException("Book with isbn " + bookDTO.getIsbn() + " already exist");
		}
		Book book = bookMapper.toEntity(bookDTO);
		book.isAvailabeCopiesValid();
		Book savedBook = bookRepository.save(book);
		return bookMapper.toDTO(savedBook);
	}

	@Override
	public List<BookDTO> createBooksBulk(List<BookDTO> bookDTOs) throws BookException {
		List<BookDTO> createdBooks = new ArrayList<>();
		for (BookDTO bookDTO : bookDTOs) {
			BookDTO book = createBook(bookDTO);
			createdBooks.add(book);
		}
		return createdBooks;
	}

	@Override
	public BookDTO getBookById(Long bookId) throws BookException {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookException("Book with id " + bookId + " not found"));

		return bookMapper.toDTO(book);
	}

	@Override
	public BookDTO getBookByISBN(String isbn) throws BookException {

		Book book = bookRepository.findByIsbn(isbn)
				.orElseThrow(() -> new BookException("Book with ISBN " + isbn + " not found"));

		return bookMapper.toDTO(book);

	}

	@Override
	public BookDTO updateBook(Long bookId, BookDTO bookDTO) throws BookException {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookException("Book with id " + bookId + " not found"));
		bookMapper.updateEntityFromDTO(book, bookDTO);

		book.isAvailabeCopiesValid();

		Book updatedBook = bookRepository.save(book);

		return bookMapper.toDTO(updatedBook);
	}

	@Override
	public void deleteBook(Long bookId) throws BookException {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookException("Book with id " + bookId + " not found"));
		book.setActive(false);
		bookRepository.save(book);

	}

	@Override
	public void hardDeleteBook(Long bookId) throws BookException {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookException("Book with id " + bookId + " not found"));
		bookRepository.delete(book);
	}

	@Override
	public PageResponse<BookDTO> searchBooksWithFilters(BookSearchRequest searchRequest) {

		Pageable pageable = createPageable(searchRequest.getPage(), searchRequest.getSize(), searchRequest.getSortBy(),
				searchRequest.getSortDirections());

		Page<Book> bookPage = bookRepository.searchBookWithFilters(searchRequest.getSearchTerm(),
				searchRequest.getGenreId(), searchRequest.getAvailableOnly(), pageable);

		return convertToPageResponse(bookPage);
	}

	@Override
	public long getTotalActiveBooks() {
		return bookRepository.countByActiveTrue();
	}

	@Override
	public long getTotalAvailableBooks() {
		return bookRepository.countAvailableBooks();
	}

	private Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
		size = Math.min(size, 10);
		size = Math.max(size, 1);

		Sort sort = sortDirection.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		return PageRequest.of(page, size, sort);

	}

	private PageResponse<BookDTO> convertToPageResponse(Page<Book> books) {

		List<BookDTO> content = books.getContent().stream().map(bookMapper::toDTO).toList();

		return PageResponse.<BookDTO>builder().content(content).pageNumber(books.getNumber()).pageSize(books.getSize())
				.totalElements(books.getTotalElements()).totalPages(books.getTotalPages()).last(books.isLast())
				.first(books.isFirst()).empty(books.isEmpty()).build();

	}

}
