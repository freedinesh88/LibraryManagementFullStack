package com.dinesh.LibraryManagementSystem.controller;

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

import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.BookReviewDTO;
import com.dinesh.LibraryManagementSystem.payload.request.CreateReviewRequest;
import com.dinesh.LibraryManagementSystem.payload.request.UpdateReviewRequest;
import com.dinesh.LibraryManagementSystem.payload.response.ApiResponse;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.service.BookReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reviews")
public class BookReviewController {

	private final BookReviewService bookReviewService;

	@PostMapping
	public ResponseEntity<?> createReview(@Valid @RequestBody CreateReviewRequest request)
			throws UserException, Exception {
		BookReviewDTO reviewDTO = bookReviewService.createReview(request);
		return ResponseEntity.ok(reviewDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateReview(@PathVariable Long id, @Valid @RequestBody UpdateReviewRequest request)
			throws UserException, Exception {
		BookReviewDTO reviewDTO = bookReviewService.updateReview(id, request);
		return ResponseEntity.ok(reviewDTO);
	}

	@DeleteMapping("/{reviewId}")
	public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) throws UserException, Exception {
		bookReviewService.deleteReview(reviewId);
		return ResponseEntity.ok(new ApiResponse("review deleted successfuy", true));
	}

	@GetMapping("/book/{bookId}")
	public ResponseEntity<PageResponse<BookReviewDTO>> getReviewsByBook(@PathVariable Long bookId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) throws Exception {
		PageResponse<BookReviewDTO> reviews = bookReviewService.getReviewsByBookId(bookId, page, size);
		return ResponseEntity.ok(reviews);

	}

}
