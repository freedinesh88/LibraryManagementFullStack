package com.dinesh.LibraryManagementSystem.mapper;

import org.springframework.stereotype.Component;

import com.dinesh.LibraryManagementSystem.model.WishList;
import com.dinesh.LibraryManagementSystem.payload.dto.WishListDTO;

@Component
public class WishListMapper {
	private BookMapper bookMapper;

	public WishListDTO toDTO(WishList wishList) {

		if (wishList == null) {
			return null;
		}
		WishListDTO dto = new WishListDTO();
		dto.setId(wishList.getId());
		if (wishList.getUser() != null) {
			dto.setUserId(wishList.getUser().getId());
			dto.setUserFullName(wishList.getUser().getFullName());

		}
		if (wishList.getBook() != null) {
			dto.setBook(bookMapper.toDTO(wishList.getBook()));
		}
		dto.setAddedAt(wishList.getAddedAt());
		dto.setNotes(wishList.getNotes());
		return dto;
	}

}
