package com.dinesh.LibraryManagementSystem.service.impl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dinesh.LibraryManagementSystem.domain.UserRole;
import com.dinesh.LibraryManagementSystem.model.User;
import com.dinesh.LibraryManagementSystem.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {

		initializeAdminUser();

	}

	private void initializeAdminUser() {

		String adminEmail = "dineshsahoo1502@gmail.com";
		String adminPassword ="Admin@123";

		if (userRepository.findByEmail(adminEmail) == null) {

			User user = User.builder().email(adminEmail).password(passwordEncoder.encode(adminPassword))
					.fullName("Admin").role(UserRole.ROLE_ADMIN).phone("9876543210").build();

			userRepository.save(user);

			System.out.println("Admin user created successfully!");

		} else {

			System.out.println("Admin user already exists.");

		}
	}
}