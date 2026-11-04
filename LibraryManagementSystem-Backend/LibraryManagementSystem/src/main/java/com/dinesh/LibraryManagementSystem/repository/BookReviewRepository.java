package com.dinesh.LibraryManagementSystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dinesh.LibraryManagementSystem.model.Book;
import com.dinesh.LibraryManagementSystem.model.BookReview;

public interface BookReviewRepository extends JpaRepository<BookReview, Long> {

	Page<BookReview> findByBook(Book book, Pageable pageable);

	boolean existsByUserIdAndBookId(Long userId, Long bookId);

}
