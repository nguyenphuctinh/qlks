package com.ptit.khachsan.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.khachsan.models.Room;
import com.ptit.khachsan.models.RoomType;
import com.ptit.khachsan.repository.RoomRepository;
import com.ptit.khachsan.repository.RoomTypeRepository;
@Service
public class RoomTypeService {
	@Autowired private RoomTypeRepository roomTypeRepo;

	public ArrayList<RoomType> getAllRoomTypes() {
		// TODO Auto-generated method stub
		ArrayList<RoomType> roomTypes = (ArrayList<RoomType>) roomTypeRepo.findAll();
		return roomTypes;
	}

	public RoomType save(RoomType roomType) {
		// TODO Auto-generated method stub
	 return roomTypeRepo.save(roomType);
	
	}
	public RoomType findById(int id) {
		// TODO Auto-generated method stub
		return roomTypeRepo.findById(id).isPresent() ? 
				roomTypeRepo.findById(id).get(): null;
		
	}

	public void delete(RoomType roomType) {
		// TODO Auto-generated method stub
		roomTypeRepo.delete(roomType);
	}
}
