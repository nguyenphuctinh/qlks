package com.ptit.khachsan.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.khachsan.models.Booking;
import com.ptit.khachsan.repository.BookingRepository;

@Service

public class BookingService {
	@Autowired
	private BookingRepository bookingRepo;

	public void save(Booking booking) {
		// TODO Auto-generated method stub
		bookingRepo.save(booking);
	}

	public ArrayList<Booking> getAllBookings() {
		// TODO Auto-generated method stub
		return (ArrayList<Booking>) bookingRepo.findAll();
	
	}

	public Booking findById(int id) {
		return bookingRepo.findById(id).get();
	}
}
