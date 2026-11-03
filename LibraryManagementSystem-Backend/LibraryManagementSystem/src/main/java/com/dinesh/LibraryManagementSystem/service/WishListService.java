package com.dinesh.LibraryManagementSystem.service;

import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.WishListDTO;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;

public interface WishListService {

	WishListDTO addToWishList(Long bookId, String notes) throws UserException, Exception;

	void removeFromWishList(Long bookId) throws UserException, Exception;

	PageResponse<WishListDTO> getMyWishList(int page, int size) throws UserException;

}
