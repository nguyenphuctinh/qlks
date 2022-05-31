package com.ptit.khachsan.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.khachsan.models.Room;
import com.ptit.khachsan.repository.RoomRepository;

@Service

public class RoomService {
	@Autowired private RoomRepository roomRepo;

	public ArrayList<Room> getAllRooms() {
		// TODO Auto-generated method stub
		ArrayList<Room> rooms = (ArrayList<Room>) roomRepo.findAll();
		return rooms;
	}

	public Room save(Room room) {
		// TODO Auto-generated method stub
		return roomRepo.save(room);
	}

	public Room findById(int parseInt) {
		// TODO Auto-generated method stub
		Optional<Room> room = roomRepo.findById(parseInt);
		if(room.isPresent()) {
			return room.get();
		}
		return null;
	}

	public void delete(Room room) {
		// TODO Auto-generated method stub
		roomRepo.delete(room);
	}


	
}
