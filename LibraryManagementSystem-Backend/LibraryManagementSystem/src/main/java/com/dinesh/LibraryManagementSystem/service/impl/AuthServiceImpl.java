package com.dinesh.LibraryManagementSystem.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dinesh.LibraryManagementSystem.configurations.JwtProvider;
import com.dinesh.LibraryManagementSystem.domain.UserRole;
import com.dinesh.LibraryManagementSystem.exception.UserException;
import com.dinesh.LibraryManagementSystem.mapper.UserMapper;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.payload.dto.UserDTO;
import com.dinesh.LibraryManagementSystem.payload.response.AuthResponse;
import com.dinesh.LibraryManagementSystem.repository.UserRepository;
import com.dinesh.LibraryManagementSystem.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final CustomUserServiceImplementation customUserServiceImplementation;

	@Override
	public AuthResponse login(String userName, String password) throws UserException {

		Authentication authentication = authenticate(userName, password);

		SecurityContextHolder.getContext().setAuthentication(authentication);
//		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//		String role = authorities.iterator().next().getAuthority();

		String token = jwtProvider.generateToken(authentication);

		User user = userRepository.findByEmail(userName);
		user.setLastLogin(LocalDateTime.now());
		userRepository.save(user);
		AuthResponse response = new AuthResponse();
		response.setTitle("Login success");
		response.setMessage("Welcome back " + userName);
		response.setJwt(token);
		response.setUser(UserMapper.toDto(user));

		return response;
	}

	private Authentication authenticate(String userName, String password) throws UserException {
		UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(userName);
		if (userDetails == null) {
			throw new UserException("User not found with - " + password);
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new UserException("Password doesnot match");
		}
		return new UsernamePasswordAuthenticationToken(userName, null, userDetails.getAuthorities());

	}

	@Override
	public AuthResponse signup(UserDTO req) throws UserException {
		User user = userRepository.findByEmail(req.getEmail());
		if (user == null) {
			throw new UserException("Email id already registered");
		}
		User createdUser = new User();
		createdUser.setEmail(req.getEmail());
		createdUser.setPassword(passwordEncoder.encode(req.getPassword()));
		createdUser.setPhone(req.getPhone());
		createdUser.setFullName(req.getFullName());
		createdUser.setLastLogin(LocalDateTime.now());
		createdUser.setRole(UserRole.ROLE_USER);
		User savedUser = userRepository.save(createdUser);
		Authentication auth = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());

		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = jwtProvider.generateToken(auth);
		AuthResponse response = new AuthResponse();
		response.setJwt(jwt);
		response.setTitle("Welcome " + createdUser.getFullName());
		response.setMessage("Register success!!!");
		response.setUser(UserMapper.toDto(savedUser));
		return response;
	}

	@Override
	public void createPasswordResetToken(String email) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetPassword(String token, String newPassword) {
		// TODO Auto-generated method stub

	}

}
