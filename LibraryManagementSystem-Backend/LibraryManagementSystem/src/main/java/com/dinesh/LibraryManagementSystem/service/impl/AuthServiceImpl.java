package com.dinesh.LibraryManagementSystem.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.configurations.JwtProvider;
import com.dinesh.LibraryManagementSystem.domain.UserRole;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.mapper.UserMapper;
import com.dinesh.LibraryManagementSystem.model.PasswordResetToken;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.dto.UserDTO;
import com.dinesh.LibraryManagementSystem.payload.response.AuthResponse;
import com.dinesh.LibraryManagementSystem.repository.PasswordResetTokenRepository;
import com.dinesh.LibraryManagementSystem.repository.UserRepository;
import com.dinesh.LibraryManagementSystem.service.AuthService;
import com.dinesh.LibraryManagementSystem.service.EmailService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final CustomUserServiceImplementation customUserServiceImplementation;
	private final PasswordResetTokenRepository passwordResetTokenRepository;
	private final EmailService emailService;

	// =========================
	// LOGIN
	// =========================

	@Override
	public AuthResponse login(String userName, String password) throws UserException {

		Authentication authentication = authenticate(userName, password);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		// Currently using email as username for login
		User user = userRepository.findByEmail(userName);

		if (user == null) {
			throw new UserException("User not found with email - " + userName);
		}

		user.setLastLogin(LocalDateTime.now());
		userRepository.save(user);

		AuthResponse response = new AuthResponse();

		response.setTitle("Login success");
		response.setMessage("Welcome back " + userName);
		response.setJwt(token);
		response.setUser(UserMapper.toDto(user));

		return response;
	}

	// =========================
	// AUTHENTICATE USER
	// =========================

	private Authentication authenticate(String userName, String password) throws UserException {

		UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(userName);

		if (userDetails == null) {
			throw new UserException("User not found with - " + userName);
		}

		if (!passwordEncoder.matches(password, userDetails.getPassword())) {

			throw new UserException("Password does not match");
		}

		return new UsernamePasswordAuthenticationToken(userName, null, userDetails.getAuthorities());
	}

	// =========================
	// SIGNUP
	// =========================

	@Override
	public AuthResponse signup(UserDTO req) throws UserException {

		// Check if email already exists
		User existingUser = userRepository.findByEmail(req.getEmail());

		if (existingUser != null) {
			throw new UserException("Email id already registered");
		}

		User createdUser = new User();

		createdUser.setEmail(req.getEmail());

		// Encode password before saving
		createdUser.setPassword(passwordEncoder.encode(req.getPassword()));

		createdUser.setPhone(req.getPhone());
		createdUser.setFullName(req.getFullName());

		// New user gets ROLE_USER
		createdUser.setRole(UserRole.ROLE_USER);

		User savedUser = userRepository.save(createdUser);

		// Create authentication for JWT
		Authentication auth = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), null,
				Collections.emptyList());

		SecurityContextHolder.getContext().setAuthentication(auth);

		String jwt = jwtProvider.generateToken(auth);

		AuthResponse response = new AuthResponse();

		response.setJwt(jwt);
		response.setTitle("Welcome " + savedUser.getFullName());
		response.setMessage("Register success!!!");
		response.setUser(UserMapper.toDto(savedUser));

		return response;
	}

	// =========================
	// FORGOT PASSWORD
	// =========================

	@Transactional
	public void createPasswordResetToken(String email) throws UserException {

		String frontendUrl = "http://localhost:5173/reset-password?token=";

		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UserException("User not found with given email");
		}

		String token = UUID.randomUUID().toString();

		PasswordResetToken resetToken = PasswordResetToken.builder().token(token).user(user)
				.expiryDate(LocalDateTime.now().plusMinutes(5)).build();

		passwordResetTokenRepository.save(resetToken);

		String resetLink = frontendUrl + token;

		String subject = "Password reset request";

		String body = """
				Hello %s,

				We received a request to reset your password.

				Click the link below to reset your password:

				%s

				This link will expire in 5 minutes.

				If you did not request a password reset, please ignore this email.

				Regards,
				Library Management System
				""".formatted(user.getFullName(), resetLink);

		emailService.sendEmail(user.getEmail(), subject, body);
	}

	// =========================
	// RESET PASSWORD
	// =========================

	@Transactional
	public void resetPassword(String token, String newPassword) throws UserException {

		PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
				.orElseThrow(() -> new UserException("Token not valid"));

		// Check token expiry
		if (resetToken.isExpired()) {

			passwordResetTokenRepository.delete(resetToken);

			throw new UserException("Password reset token has expired");
		}

		User user = resetToken.getUser();

		// Encode new password
		user.setPassword(passwordEncoder.encode(newPassword));

		userRepository.save(user);

		// Delete token after successful reset
		passwordResetTokenRepository.delete(resetToken);
	}
}