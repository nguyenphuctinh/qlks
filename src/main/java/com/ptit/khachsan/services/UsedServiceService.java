package com.ptit.khachsan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.khachsan.models.UsedService;
import com.ptit.khachsan.repository.UsedServiceRepository;

@Service
public class UsedServiceService {
	@Autowired
	private  UsedServiceRepository usedServiceRepository;

	public UsedService save(UsedService usedService) {
		// TODO Auto-generated method stub
		return usedServiceRepository.save(usedService);
	}

	
	
}
