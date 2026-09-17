package com.dinesh.LibraryManagementSystem.service;

import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.UserDTO;
import com.dinesh.LibraryManagementSystem.payload.response.AuthResponse;

public interface AuthService {

	AuthResponse login(String userName, String password) throws UserException;

	AuthResponse signup(UserDTO req) throws UserException;

	void createPasswordResetToken(String email);
	void resetPassword(String token, String newPassword);
	

}
