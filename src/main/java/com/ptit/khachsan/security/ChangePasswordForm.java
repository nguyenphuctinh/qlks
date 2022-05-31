package com.ptit.khachsan.security;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ptit.khachsan.models.User;

import lombok.Data;

@Data
public class ChangePasswordForm {

	@NotNull
	private String password;
	@NotNull
	@Size(min = 3, message = "Password phải ít nhất 3 ký tự")
	private String newPassword;
	@NotNull
	private String confirmNewPassword;

	public User toUser(PasswordEncoder passwordEncoder, User user) {
		
		user.setPassword( passwordEncoder.encode(newPassword));
	
		return user;
	}

	public boolean isMatched(PasswordEncoder passwordEncoder, String rawPassword, String encodedPassword) {
	
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}
