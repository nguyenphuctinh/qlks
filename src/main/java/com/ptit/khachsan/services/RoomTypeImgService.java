package com.ptit.khachsan.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.khachsan.models.RoomTypeImg;
import com.ptit.khachsan.repository.RoomRepository;
import com.ptit.khachsan.repository.RoomTypeImgRepository;

@Service
public class RoomTypeImgService {
	@Autowired private RoomTypeImgRepository roomTypeImgRepo;

	public RoomTypeImg save(RoomTypeImg roomImg) {
		// TODO Auto-generated method stub
		RoomTypeImg save = roomTypeImgRepo.save(roomImg);
		return save;
	}
}
