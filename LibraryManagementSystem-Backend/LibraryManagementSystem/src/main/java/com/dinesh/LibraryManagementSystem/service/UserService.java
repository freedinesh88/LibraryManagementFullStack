package com.dinesh.LibraryManagementSystem.service;

import java.util.List;

import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.dto.UserDTO;

public interface UserService {

	public User getCurrentUser() throws UserException;

	public List<UserDTO> getAllUsers();

}
