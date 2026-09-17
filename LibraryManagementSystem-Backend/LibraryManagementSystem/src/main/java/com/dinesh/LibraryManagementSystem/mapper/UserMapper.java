package com.dinesh.LibraryManagementSystem.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.dto.UserDTO;

@Service
public class UserMapper {

	public static UserDTO toDto(User user) {

		if (user == null) {
			return null;
		}

		UserDTO userDTO = new UserDTO();

		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setFullName(user.getFullName());
		userDTO.setRole(user.getRole());
		userDTO.setPhone(user.getPhone());
		userDTO.setLastLogIn(user.getLastLogin());

		return userDTO;
	}

	public static List<UserDTO> toDtoList(List<User> users) {

		if (users == null) {
			return null;
		}

		return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
	}

	public static Set<UserDTO> toDtoSet(Set<User> users) {

		if (users == null) {
			return null;
		}

		return users.stream().map(UserMapper::toDto).collect(Collectors.toSet());
	}

	public static User toEntity(UserDTO userDTO) {

		if (userDTO == null) {
			return null;
		}

		User createdUser = new User();
		createdUser.setEmail(userDTO.getEmail());
		createdUser.setPassword(userDTO.getPassword());
		createdUser.setCreatedAt(LocalDateTime.now());
		createdUser.setPhone(userDTO.getPhone());
		createdUser.setFullName(userDTO.getFullName());
		createdUser.setRole(userDTO.getRole());

		return createdUser;
	}
}