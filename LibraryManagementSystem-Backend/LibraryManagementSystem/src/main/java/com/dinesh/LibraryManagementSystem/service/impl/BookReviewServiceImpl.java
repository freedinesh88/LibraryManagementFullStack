package com.dinesh.LibraryManagementSystem.service.impl;

import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.model.Book;
import com.dinesh.LibraryManagementSystem.model.BookReview;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.dto.BookReviewDTO;
import com.dinesh.LibraryManagementSystem.payload.request.CreateReviewRequest;
import com.dinesh.LibraryManagementSystem.payload.request.UpdateReviewRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.repository.BookRepository;
import com.dinesh.LibraryManagementSystem.repository.BookReviewRepository;
import com.dinesh.LibraryManagementSystem.service.BookReviewService;
import com.dinesh.LibraryManagementSystem.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookReviewServiceImpl implements BookReviewService {

	private final BookReviewRepository bookReviewRepository;
	private final UserService userService;
	private final BookRepository bookRepository;

	@Override
	public BookReviewDTO createReview(CreateReviewRequest request) throws UserException, Exception {
		User user = userService.getCurrentUser();
		Book book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new Exception("Book not found!!!"));
		if (bookReviewRepository.existsByUserIdAndBookId(user.getId(), book.getId())) {
			throw new Exception("You have already reviewed this book");
		}
		boolean hasReadBook = hasUserReadBook(user.getId(), book.getId());
		if (hasReadBook) {
			throw new Exception("you have not read this book");
		}
		BookReview bookReview = new BookReview();
		bookReview.setUser(user);
		bookReview.setBook(book);
		bookReview.setRating(request.getRating());
		bookReview.setReviewText(request.getReviewText());
		bookReview.setTitle(request.getTitle());
		BookReview savedBookReview = bookReviewRepository.save(bookReview);
		return null;
	}

	@Override
	public BookReviewDTO updateReview(Long reviewId, UpdateReviewRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteReview(Long reviewId) {
		// TODO Auto-generated method stub

	}

	@Override
	public PageResponse<BookReviewDTO> getReviewsByBookId(Long id, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean hasUserReadBook(Long id, Long id2) {
		return false;
	}

}
