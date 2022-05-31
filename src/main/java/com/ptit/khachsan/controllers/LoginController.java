package com.ptit.khachsan.controllers;

import java.util.ArrayList;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ptit.khachsan.models.User;
import com.ptit.khachsan.services.UserRepositoryUserDetailsService;

@Controller
public class LoginController {
	@Autowired
	private UserRepositoryUserDetailsService userService;

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@GetMapping("/login-error")
	public String login(Model model) {
		model.addAttribute("error", "Tài khoản hoặc mật khẩu không chính xác");
		return "login";
	}

}
