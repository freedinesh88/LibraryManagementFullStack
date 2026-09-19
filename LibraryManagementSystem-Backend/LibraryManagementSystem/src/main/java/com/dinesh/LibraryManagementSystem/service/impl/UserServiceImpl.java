package com.dinesh.LibraryManagementSystem.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.mapper.UserMapper;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.repository.UserRepository;
import com.dinesh.LibraryManagementSystem.payload.dto.UserDTO;
import com.dinesh.LibraryManagementSystem.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public User getCurrentUser() throws UserException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new UserException("User is not authenticated");
		}

		String email = authentication.getName();

		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UserException("User not found with email: " + email);
		}

		return user;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();

		return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
	}
}