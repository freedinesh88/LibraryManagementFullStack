package com.dinesh.LibraryManagementSystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dinesh.LibraryManagementSystem.model.WishList;

public interface WishListRepository extends JpaRepository<WishList, Long> {

	Page<WishList> findByUserId(Long userId, Pageable pageable);

	boolean existsByUserIdAndBookId(Long userId, Long bookId);

	void deleteByUserIdAndBookId(Long userId, Long bookId);

}
