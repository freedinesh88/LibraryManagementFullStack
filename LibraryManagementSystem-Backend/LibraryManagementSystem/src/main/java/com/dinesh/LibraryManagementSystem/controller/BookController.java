package com.dinesh.LibraryManagementSystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.LibraryManagementSystem.exception.BookException;
import com.dinesh.LibraryManagementSystem.mapper.BookMapper;
import com.dinesh.LibraryManagementSystem.model.Book;
import com.dinesh.LibraryManagementSystem.payload.dto.BookDTO;
import com.dinesh.LibraryManagementSystem.payload.request.BookSearchRequest;
import com.dinesh.LibraryManagementSystem.payload.response.ApiResponse;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;
	@PostMapping
	private ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) throws BookException {
		BookDTO createdBook = bookService.createBook(bookDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
	}

	@PostMapping("/bulk")
	public ResponseEntity<List<BookDTO>> createBookBulks(@Valid @RequestBody List<BookDTO> bookDTOs)
			throws BookException {

		List<BookDTO> createdBooks = bookService.createBooksBulk(bookDTOs);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdBooks);
	}

	@GetMapping("/{bookId}")
	public ResponseEntity<BookDTO> getBookById(@PathVariable Long bookId) throws BookException {

		BookDTO book = bookService.getBookById(bookId);

		return ResponseEntity.ok(book);

	}

	@GetMapping("/isbn/{isbn}")
	public ResponseEntity<BookDTO> getBookByISBN(@PathVariable String isbn) throws BookException {

		BookDTO book = bookService.getBookByISBN(isbn);

		return ResponseEntity.ok(book);

	}

	@PutMapping("/{bookId}")
	public ResponseEntity<BookDTO> updateBook(@PathVariable Long bookId, @Valid @RequestBody BookDTO bookDTO)
			throws BookException {

		BookDTO updatedBook = bookService.updateBook(bookId, bookDTO);

		return ResponseEntity.ok(updatedBook);

	}

	@DeleteMapping("/{bookId}")
	public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long bookId) throws BookException {

		bookService.deleteBook(bookId);

		return ResponseEntity.ok(new ApiResponse("Book deleted successfully", true));

	}

	@DeleteMapping("/{bookId}/permanent")
	public ResponseEntity<ApiResponse> hardDeleteBook(@PathVariable Long bookId) throws BookException {

		bookService.hardDeleteBook(bookId);

		return ResponseEntity.ok(new ApiResponse("Book deleted successfully", true));

	}
	
	@GetMapping
	public ResponseEntity<PageResponse<BookDTO>> searchBooks(
	        @RequestParam(required = false) String searchTerm,
	        @RequestParam(required = false) Long genreId,
	        @RequestParam(required = false,defaultValue = "false") Boolean availableOnly,
	        @RequestParam(defaultValue = "0") Integer page,
	        @RequestParam(defaultValue = "20") Integer size,
	        @RequestParam(defaultValue = "createdAt") String sortBy,
	        @RequestParam(defaultValue = "DESC") String sortDirections) {

	    BookSearchRequest searchRequest = new BookSearchRequest();

	    searchRequest.setSearchTerm(searchTerm);
	    searchRequest.setGenreId(genreId);
	    searchRequest.setAvailableOnly(availableOnly);
	    searchRequest.setPage(page);
	    searchRequest.setSize(size);
	    searchRequest.setSortBy(sortBy);
	    searchRequest.setSortDirections(sortDirections);

	    PageResponse<BookDTO> response =
	            bookService.searchBooksWithFilters(searchRequest);

	    return ResponseEntity.ok(response);
	}

	@GetMapping("/search")
	public ResponseEntity<PageResponse<BookDTO>> advancedSearch(@Valid BookSearchRequest searchRequest) {
		PageResponse<BookDTO> response = bookService.searchBooksWithFilters(searchRequest);

		return ResponseEntity.ok(response);

	}

	@GetMapping("/stats")
	public ResponseEntity<BookStatsResponse> getBookStats() {
		long totalActive = bookService.getTotalActiveBooks();
		long totalAvailable = bookService.getTotalAvailableBooks();
		BookStatsResponse stats = new BookStatsResponse(totalActive, totalAvailable);
		return ResponseEntity.ok(stats);
	}

	public static class BookStatsResponse {
		public long totalActiveBooks;
		public long totalAvailableBooks;

		public BookStatsResponse(long totalActiveBooks, long totalAvailableBooks) {
			this.totalActiveBooks = totalActiveBooks;
			this.totalAvailableBooks = totalAvailableBooks;
		}
	}

}
