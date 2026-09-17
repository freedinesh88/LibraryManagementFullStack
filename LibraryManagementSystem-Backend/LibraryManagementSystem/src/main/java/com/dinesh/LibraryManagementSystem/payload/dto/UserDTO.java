package com.dinesh.LibraryManagementSystem.payload.dto;

import java.time.LocalDateTime;

import com.dinesh.LibraryManagementSystem.domain.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Long id;
	private String email;
	private String password;
	private String phone;
	private String fullName;
	private UserRole role;
	private String userName;

	private LocalDateTime lastLogIn;

}
