package com.dinesh.LibraryManagementSystem.payload.dto;

import java.time.LocalDateTime;

import com.dinesh.LibraryManagementSystem.domain.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 100,
          message = "Password must be between 8 and 100 characters")
    private String password;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Phone number must be a valid 10-digit Indian mobile number"
    )
    private String phone;

    @NotBlank(message = "Full name is mandatory")
    @Size(min = 2, max = 100,
          message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotNull(message = "Role is mandatory")
    private UserRole role;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50,
          message = "Username must be between 3 and 50 characters")
    private String userName;

    private LocalDateTime lastLogIn;
}
