package com.dinesh.LibraryManagementSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.payload.dto.UserDTO;
import com.dinesh.LibraryManagementSystem.payload.request.ForgotPasswordRequest;
import com.dinesh.LibraryManagementSystem.payload.request.LoginRequest;
import com.dinesh.LibraryManagementSystem.payload.request.ResetPasswordRequest;
import com.dinesh.LibraryManagementSystem.payload.response.AuthResponse;
import com.dinesh.LibraryManagementSystem.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signupHandler(@RequestBody @Valid UserDTO userDTO) throws UserException {

		AuthResponse response = authService.signup(userDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody @Valid LoginRequest loginRequest)
			throws UserException {

		AuthResponse response = authService.login(loginRequest.getEmail(), loginRequest.getPassword());

		return ResponseEntity.ok(response);
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPasswordHandler(@RequestBody @Valid ForgotPasswordRequest request)
			throws UserException {

		authService.createPasswordResetToken(request.getEmail());

		return ResponseEntity.ok("Password reset link has been sent to your email");
	}

	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPasswordHandler(@RequestBody @Valid ResetPasswordRequest request)
			throws UserException {

		authService.resetPassword(request.getToken(), request.getNewPassword());

		return ResponseEntity.ok("Password reset successfully");
	}

}
