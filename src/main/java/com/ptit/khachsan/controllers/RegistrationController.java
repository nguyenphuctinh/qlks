package com.ptit.khachsan.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ptit.khachsan.repository.UserRepository;
import com.ptit.khachsan.security.RegistrationForm;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(
            UserRepository userRepo, PasswordEncoder passwordEncoder) {
    	//System.out.println("resgister "+passwordEncoder);
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("registrationForm" , new RegistrationForm());
        return "register";
    }

    @PostMapping
    public String processRegistration(Model model,
    		RedirectAttributes ra,
    		@Valid RegistrationForm form, Errors errors) {
        if (errors.hasErrors()) {
         
            return "register";
        }
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("error", "Mật khẩu nhập lại không khớp!");
            return "register";
        }
        if (userRepo.findByUsername(form.getUsername()) != null) {
            model.addAttribute("error", "Tài khoản đã tồn tại!");
            return "register";
        }
        ra.addFlashAttribute("message", "Đăng ký thành công!");
        userRepo.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }

}
