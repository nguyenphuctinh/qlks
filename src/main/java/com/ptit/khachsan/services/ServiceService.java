package com.ptit.khachsan.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.khachsan.models.Room;
import com.ptit.khachsan.repository.RoomRepository;
import com.ptit.khachsan.repository.ServiceRepository;

@Service
public class ServiceService {
	@Autowired private ServiceRepository serviceRepo;

	public ArrayList<com.ptit.khachsan.models.Service> getAllServices() {
		return  (ArrayList<com.ptit.khachsan.models.Service>) serviceRepo.findAll();
	}

	public void save(com.ptit.khachsan.models.Service service) {
		// TODO Auto-generated method stub
		serviceRepo.save(service);
	}

	public com.ptit.khachsan.models.Service findById(int id) {
		// TODO Auto-generated method stub
		return serviceRepo.findById(id).isPresent() ? 
				serviceRepo.findById(id).get(): null;
		
	}

	public void delete(com.ptit.khachsan.models.Service service) {
		// TODO Auto-generated method stub
		serviceRepo.delete(service);
	}

	public ArrayList<com.ptit.khachsan.models.Service> findAllService() {
		// TODO Auto-generated method stub
		return (ArrayList<com.ptit.khachsan.models.Service>) serviceRepo.findAll();
	}
	
}
