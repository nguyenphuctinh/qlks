package com.ptit.khachsan.security;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ptit.khachsan.models.User;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class RegistrationForm {
  @NotNull
  @Size(min=3, message="Username phải ít nhất 3 ký tự")
  private String username;
  @NotNull
  @Size(min=3, message="Password phải ít nhất 3 ký tự")
  private String password;
  private String confirmPassword;
  @NotBlank (message="Tên không được bỏ trống")
  private String fullName;
  private String role;
  public User toUser(PasswordEncoder passwordEncoder) {
    return new User(
        username, passwordEncoder.encode(password),fullName,"ROLE_CUSTOMER");
  }
 
}