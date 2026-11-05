package com.dinesh.LibraryManagementSystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.domain.BookLoanStatus;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.mapper.BookReviewMapper;
import com.dinesh.LibraryManagementSystem.model.Book;
import com.dinesh.LibraryManagementSystem.model.BookLoan;
import com.dinesh.LibraryManagementSystem.model.BookReview;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.dto.BookReviewDTO;
import com.dinesh.LibraryManagementSystem.payload.request.CreateReviewRequest;
import com.dinesh.LibraryManagementSystem.payload.request.UpdateReviewRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.repository.BookLoanRepository;
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
	private final BookReviewMapper bookReviewMapper;
	private final BookLoanRepository bookLoanRepository;

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
		return bookReviewMapper.toDTO(savedBookReview);
	}

	@Override
	public BookReviewDTO updateReview(Long reviewId, UpdateReviewRequest request) throws UserException, Exception {
		User user = userService.getCurrentUser();
		BookReview bookReview = bookReviewRepository.findById(reviewId)
				.orElseThrow(() -> new Exception("Review not found"));
		if (!bookReview.getUser().getId().equals(user.getId())) {
			throw new Exception("You have not reviewed this book!");
		}
		bookReview.setReviewText(request.getReviewText());
		bookReview.setTitle(request.getTitle());
		bookReview.setRating(request.getRating());
		BookReview savedBookReview = bookReviewRepository.save(bookReview);
		return bookReviewMapper.toDTO(savedBookReview);
	}

	@Override
	public void deleteReview(Long reviewId) throws UserException, Exception {
		User currentUser = userService.getCurrentUser();
		BookReview bookReview = bookReviewRepository.findById(reviewId)
				.orElseThrow(() -> new Exception("Review not found"));
		if (!bookReview.getUser().getId().equals(currentUser.getId())) {
			throw new Exception("You can delete only your review");
		}
		bookReviewRepository.delete(bookReview);

	}

	@Override
	public PageResponse<BookReviewDTO> getReviewsByBookId(Long id, int page, int size) throws Exception {
		Book book = bookRepository.findById(id).orElseThrow(() -> new Exception("Book not found by id!"));
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<BookReview> reviewPage = bookReviewRepository.findByBook(book, pageable);
		return convertToPageReview(reviewPage);
	}

	private PageResponse<BookReviewDTO> convertToPageReview(Page<BookReview> reviewPage) {
		List<BookReviewDTO> reviewDTOs = reviewPage.getContent().stream().map(bookReviewMapper::toDTO)
				.collect(Collectors.toList());
		return new PageResponse<>(reviewDTOs, reviewPage.getNumber(), reviewPage.getSize(),
				reviewPage.getTotalElements(), reviewPage.getTotalPages(), reviewPage.isLast(), reviewPage.isFirst(),
				reviewPage.isEmpty());
	}

	private boolean hasUserReadBook(Long userId, Long bookId) {
		List<BookLoan> bookLoans = (List<BookLoan>) bookLoanRepository.findByBookId(bookId);
		return bookLoans.stream()
				.anyMatch(loan -> loan.getUser().getId().equals(userId) && loan.getStatus() == BookLoanStatus.RETURNED);
	}

}
