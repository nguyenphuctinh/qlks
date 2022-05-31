package com.ptit.khachsan.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ptit.khachsan.models.Room;
import com.ptit.khachsan.models.RoomType;
import com.ptit.khachsan.models.User;
import com.ptit.khachsan.services.RoomService;
import com.ptit.khachsan.services.RoomTypeService;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired private RoomTypeService roomTypeService;
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
	public String showHomePage(Model model) {
		
	
		ArrayList<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
		model.addAttribute("roomTypes", roomTypes);
		
		return "home";
	}
}
