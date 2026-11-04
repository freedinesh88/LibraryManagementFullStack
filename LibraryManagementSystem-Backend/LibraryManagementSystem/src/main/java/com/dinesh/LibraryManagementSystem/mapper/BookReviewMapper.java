package com.dinesh.LibraryManagementSystem.mapper;

import org.springframework.stereotype.Component;

import com.dinesh.LibraryManagementSystem.model.BookReview;
import com.dinesh.LibraryManagementSystem.payload.dto.BookReviewDTO;

@Component
public class BookReviewMapper {

	public BookReviewDTO toDTO(BookReview bookReview) {
		if (bookReview == null) {
			return null;
		}
		return BookReviewDTO.builder().id(bookReview.getId()).userId(bookReview.getUser().getId())
				.userName(bookReview.getUser().getFullName()).bookId(bookReview.getBook().getId())
				.bookTitle(bookReview.getBook().getTitle()).rating(bookReview.getRating())
				.reviewText(bookReview.getReviewText()).title(bookReview.getTitle())
				.createdAt(bookReview.getCreatedAt()).updatedAt(bookReview.getUpdatedAt()).build();
	}

}
