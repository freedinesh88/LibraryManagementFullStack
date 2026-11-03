package com.dinesh.LibraryManagementSystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.mapper.WishListMapper;
import com.dinesh.LibraryManagementSystem.model.Book;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.model.WishList;
import com.dinesh.LibraryManagementSystem.payload.dto.WishListDTO;
import com.dinesh.LibraryManagementSystem.payload.response.PageResponse;
import com.dinesh.LibraryManagementSystem.repository.BookRepository;
import com.dinesh.LibraryManagementSystem.repository.WishListRepository;
import com.dinesh.LibraryManagementSystem.service.UserService;
import com.dinesh.LibraryManagementSystem.service.WishListService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

	private final UserService userService;
	private final WishListRepository wishListRepository;
	private final BookRepository bookRepository;
	private final WishListMapper wishListMapper;

	@Override
	public WishListDTO addToWishList(Long bookId, String notes) throws UserException, Exception {
		User user = userService.getCurrentUser();
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new Exception("Book not found"));
		if (wishListRepository.existsByUserIdAndBookId(user.getId(), bookId)) {
			throw new Exception("Wishlist already exists");
		}
		WishList wishList = new WishList();
		wishList.setUser(user);
		wishList.setBook(book);
		wishList.setNotes(notes);
		WishList saved = wishListRepository.save(wishList);
		return wishListMapper.toDTO(wishList);
	}

	@Override
	public void removeFromWishList(Long bookId) throws UserException, Exception {
		User user = userService.getCurrentUser();
		if (!wishListRepository.existsByUserIdAndBookId(user.getId(), bookId)) {
			throw new Exception("Book is not in your wishlist");
		}
		wishListRepository.deleteByUserIdAndBookId(user.getId(), bookId);

	}

	@Override
	public PageResponse<WishListDTO> getMyWishList(int page, int size) throws UserException {
		Long userId = userService.getCurrentUser().getId();
		Pageable pageable = PageRequest.of(page, size, Sort.by("addedAt").descending());
		Page<WishList> wishListPage = wishListRepository.findByUserId(userId, pageable);
		return convertToPageResponse(wishListPage);
	}

	private PageResponse<WishListDTO> convertToPageResponse(Page<WishList> wishListPage) {
		List<WishListDTO> wishListDTOs = wishListPage.getContent().stream().map(wishListMapper::toDTO)
				.collect(Collectors.toList());

		return new PageResponse<>(wishListDTOs, wishListPage.getNumber(), wishListPage.getSize(),
				wishListPage.getTotalElements(), wishListPage.getTotalPages(), wishListPage.isLast(),
				wishListPage.isFirst(), wishListPage.isEmpty());
	}

}
