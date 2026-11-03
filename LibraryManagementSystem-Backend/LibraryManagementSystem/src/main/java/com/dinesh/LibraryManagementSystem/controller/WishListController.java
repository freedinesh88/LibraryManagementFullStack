package com.dinesh.LibraryManagementSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.WishListDTO;
import com.dinesh.LibraryManagementSystem.payload.response.ApiResponse;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.service.WishListService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishList")
public class WishListController {

	private final WishListService wishListService;

	@PostMapping("/add/{bookId}")
	public ResponseEntity<?> addToWishList(@PathVariable Long bookId, @RequestParam(required = false) String notes)
			throws UserException, Exception {
		WishListDTO wishListDTO = wishListService.addToWishList(bookId, notes);
		return ResponseEntity.ok(wishListDTO);
	}

	@DeleteMapping("/remove/{bookId}")
	public ResponseEntity<?> removeFromWishList(@PathVariable Long bookId) throws UserException, Exception {
		wishListService.removeFromWishList(bookId);
		return ResponseEntity.ok(new ApiResponse("Book successfully removed from wishlist", true));
	}

	@GetMapping("/my-wishlist")
	public ResponseEntity<?> getMyWishList(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) throws UserException {
		PageResponse<WishListDTO> wishList = wishListService.getMyWishList(page, size);
		return ResponseEntity.ok(wishList);

	}

}
