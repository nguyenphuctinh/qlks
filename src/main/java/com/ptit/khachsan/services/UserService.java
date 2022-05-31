package com.ptit.khachsan.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.khachsan.models.User;
import com.ptit.khachsan.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;

	public ArrayList<User> getAllUsers() {
		// TODO Auto-generated method stub
		ArrayList<User> users = (ArrayList<User>) userRepo.findAll();
		
		return users;
	}
	public User findById(int id) {
		Optional<User> user = userRepo.findById(id);
		if(user.isPresent() ) {
			return user.get();
		}
		return null;
	}
	public void save(User selectedUser) {
		// TODO Auto-generated method stub
		userRepo.save(selectedUser);
	}
	
}
