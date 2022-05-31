package com.ptit.khachsan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ptit.khachsan.models.User;
import com.ptit.khachsan.repository.UserRepository;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user != null) {
			return user;
		}
		System.out.println("user not found ");
		throw new UsernameNotFoundException("User '" + username + "' not found");
	}

	public void save(User user) {
		// TODO Auto-generated method stub
		userRepo.save(user);
	}

}
