package com.ptit.khachsan.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptit.khachsan.models.User;
import com.ptit.khachsan.repository.UserRepository;
import com.ptit.khachsan.security.ChangePasswordForm;
import com.ptit.khachsan.security.RegistrationForm;
import com.ptit.khachsan.services.UserRepositoryUserDetailsService;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private UserRepositoryUserDetailsService userService;
	private UserRepository userRepo;
	private PasswordEncoder passwordEncoder;

	public AccountController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@ModelAttribute
	public void addUserToModel(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user;
		if (principal instanceof UserDetails) {
			user = ((UserDetails) principal);
		} else {
			user = null;
		}
		model.addAttribute("user", user);
	}

	@GetMapping
	public String showAccount(Model model) {

		return "updateInfo";
	}

	@GetMapping("/updateInfo")
	public String showUpdateInfo(Model model) {

		return "updateInfo";
	}

	@PostMapping("/updateInfo")
	public String updateInfo(Model model, User user) {
		userService.save(user);
		model.addAttribute("message", "Cập nhật thành công!");
		return "updateInfo";
	}

	@GetMapping("/changePassword")
	public String showChangePassword(Model model) {
		ChangePasswordForm changePasswordForm = new ChangePasswordForm();
		
		model.addAttribute("changePasswordForm", changePasswordForm);
		return "changePassword";
	}

	@PostMapping("changePassword")
	public String processChangingPassword(Model model, @Valid ChangePasswordForm form, Errors errors) {
		if (errors.hasErrors()) {

			
			return "changePassword";
		}
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user;

		user = ((UserDetails) principal);

		if (!form.isMatched(passwordEncoder, form.getPassword(), user.getPassword())) {

			model.addAttribute("error", "Mật khẩu không chính xác!");
			return "changePassword";
		}
		if (!form.getNewPassword().equals(form.getConfirmNewPassword())) {
	
			model.addAttribute("error", "Mật khẩu nhập lại không khớp!");
			return "changePassword";
		}
		model.addAttribute("message", "Đổi mật khẩu thành công!");
		userRepo.save(form.toUser(passwordEncoder,(User) user));
		return "changePassword";
	}
}