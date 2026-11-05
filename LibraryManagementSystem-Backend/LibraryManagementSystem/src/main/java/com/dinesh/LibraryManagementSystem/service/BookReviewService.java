package com.dinesh.LibraryManagementSystem.service;

import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.BookReviewDTO;
import com.dinesh.LibraryManagementSystem.payload.request.CreateReviewRequest;
import com.dinesh.LibraryManagementSystem.payload.request.UpdateReviewRequest;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;

public interface BookReviewService {

	BookReviewDTO createReview(CreateReviewRequest request) throws UserException, Exception;

	BookReviewDTO updateReview(Long reviewId, UpdateReviewRequest request) throws UserException, Exception;

	void deleteReview(Long reviewId) throws UserException, Exception;

	PageResponse<BookReviewDTO> getReviewsByBookId(Long id, int page, int size) throws Exception;

}
