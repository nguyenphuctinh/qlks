package com.ptit.khachsan.services;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.khachsan.models.BookedRoom;
import com.ptit.khachsan.repository.BookedRoomRepository;

@Service
public class BookedRoomService {
	@Autowired
	private BookedRoomRepository bookedRoomRepo;

	public BookedRoom save(BookedRoom bookedRoom) {
		// TODO Auto-generated method stub
		return bookedRoomRepo.save(bookedRoom);

	}

	public ArrayList<BookedRoom> getExistingBookedRoomBetweenDateById(Date checkIn, Date checkOut, int roomTypeId) {
		return (ArrayList<BookedRoom>) bookedRoomRepo.getExistingBookedRoomBetweenDateById(checkIn, checkOut, roomTypeId);
	}
	public ArrayList<BookedRoom> selectCheckedOutBookedRooms(int year, int month) {
		System.out.println(year+" "+month);
		return (ArrayList<BookedRoom>) bookedRoomRepo.selectCheckedOutBookedRooms(month, year);
	}

	public ArrayList<BookedRoom> getAll() {
		return (ArrayList<BookedRoom>) bookedRoomRepo.findAll();
	}

	public ArrayList<BookedRoom> getExistingBookedRoomBetweenDate(Date parse, Date parse2) {
		return (ArrayList<BookedRoom>) bookedRoomRepo.getExistingBookedRoomBetweenDate(parse, parse2);
	}
}
