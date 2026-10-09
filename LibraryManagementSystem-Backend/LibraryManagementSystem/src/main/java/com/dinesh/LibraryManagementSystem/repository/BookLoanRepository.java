package com.dinesh.LibraryManagementSystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dinesh.LibraryManagementSystem.domain.BookLoanStatus;
import com.dinesh.LibraryManagementSystem.model.BookLoan;

public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {
	Page<BookLoan> findByStatus(BookLoanStatus status, Pageable pageable);

	Page<BookLoan> findByUserId(Long userId, Pageable pageable);

	Page<BookLoan> findByUserIdAndStatus(Long userId, BookLoanStatus status, Pageable pageable);
	
	@Query("select case when count(bl) > 0 then true else false end from BookLoan bl" +
	"where"
			
			)
	boolean hasActiveCheckout(@Param("userId") Long userId, @Param("bookId") Long bookId);

}
