package com.dinesh.LibraryManagementSystem.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.payload.dto.BookDTO;
import com.dinesh.LibraryManagementSystem.payload.request.BookSearchRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	@Override
	public BookDTO createBook(BookDTO bookDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookDTO> createBooksBulk() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookDTO getBookById(Long bookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookDTO getBookByISBN(String isbn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookDTO updateBook(Long bookId, BookDTO bookDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBook(Long bookId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hardDeleteBook(Long bookId) {
		// TODO Auto-generated method stub

	}

	@Override
	public PageResponse<BookDTO> searchBooksWithFilters(BookSearchRequest searchRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTotalActiveBooks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getTotalAvailableBooks() {
		// TODO Auto-generated method stub
		return 0;
	}

}
