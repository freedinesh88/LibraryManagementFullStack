package com.dinesh.LibraryManagementSystem.service;

import java.util.List;

import javax.naming.ldap.PagedResultsResponseControl;

import com.dinesh.LibraryManagementSystem.payload.dto.BookDTO;
import com.dinesh.LibraryManagementSystem.payload.request.BookSearchRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;

public interface BookService {

	BookDTO createBook(BookDTO bookDTO);

	List<BookDTO> createBooksBulk();

	BookDTO getBookById(Long bookId);

	BookDTO getBookByISBN(String isbn);

	BookDTO updateBook(Long bookId, BookDTO bookDTO);

	void deleteBook(Long bookId);

	void hardDeleteBook(Long bookId);

	PageResponse<BookDTO> searchBooksWithFilters(BookSearchRequest searchRequest);
	
	long getTotalActiveBooks();
	
	long getTotalAvailableBooks();

}
