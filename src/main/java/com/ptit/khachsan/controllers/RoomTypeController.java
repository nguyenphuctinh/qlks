package com.ptit.khachsan.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ptit.khachsan.models.BookedRoom;
import com.ptit.khachsan.models.Room;
import com.ptit.khachsan.models.RoomType;
import com.ptit.khachsan.models.RoomTypeImg;
import com.ptit.khachsan.models.Service;
import com.ptit.khachsan.services.BookedRoomService;
import com.ptit.khachsan.services.RoomService;
import com.ptit.khachsan.services.RoomTypeImgService;
import com.ptit.khachsan.services.RoomTypeService;
import com.ptit.khachsan.services.ServiceService;
import com.ptit.khachsan.utils.FileUploadUtil;

@Controller
@RequestMapping("/roomTypes")
public class RoomTypeController {
	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private RoomTypeImgService roomTypeImgService;
	@Autowired
	private ServiceService serviceService;
	@Autowired
	private BookedRoomService bookedRoomService;
	@Autowired
	private RoomService roomService;
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
	public String showRoomPage(Model model, @RequestParam(required = false) String checkIn,
			@RequestParam(required = false) String checkOut, @RequestParam(required = false) String numberOfPeople, @RequestParam(required = false) String price) {
		ArrayList<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
		
		if(numberOfPeople!=null && !numberOfPeople.isEmpty()) {
			ArrayList<RoomType> tmp = new ArrayList<RoomType>();
			for(RoomType roomType : roomTypes) {
				if(roomType.getMaxCustomer()>=Integer.parseInt(numberOfPeople)) {
					tmp.add(roomType);
				}
			}
			roomTypes=new ArrayList<>(tmp);
		}
		if((checkIn!=null && !checkIn.isEmpty())|| (checkOut!=null && !checkOut.isEmpty())) {
			if(checkIn.isEmpty() ) {
				checkIn=checkOut;
			}
			if(checkOut.isEmpty()) {
				checkOut=checkIn;
			}
			//System.out.println(checkIn+""+ checkOut);
			try {
				ArrayList<BookedRoom> bookedRooms = bookedRoomService.getExistingBookedRoomBetweenDate(new SimpleDateFormat("yyyy-MM-dd").parse(checkIn), new SimpleDateFormat("yyyy-MM-dd").parse(checkOut));
				ArrayList<RoomType> tmp = new ArrayList<RoomType>();
				ArrayList<Room> rooms = roomService.getAllRooms();
				for(RoomType roomType : roomTypes) {
					int numberOfRoom=0;
					for(Room room : rooms) {
						if(room.getRoomType().getRoomTypeId()==roomType.getRoomTypeId()) {
							numberOfRoom+=1;
						}
					}
					for(BookedRoom bookedRoom: bookedRooms) {
						if(bookedRoom.getRoom().getRoomType().getRoomTypeId()==roomType.getRoomTypeId()) {
							numberOfRoom-=1;
						}
					}
					if(numberOfRoom>0) {
						tmp.add(roomType);
					}
				}
				roomTypes=new ArrayList<RoomType>(tmp);
			} catch (ParseException e) {
				e.printStackTrace();
				return "redirect:/";
			}
		}
		if(price==null) {
			price="Tăng dần";
		}
	//		System.out.println(price);
		if(price.equals("Tăng dần")) {
			roomTypes.sort((o1, o2) -> Integer.valueOf(o1.getPrice()).compareTo(o2.getPrice()));
		}else {
			roomTypes.sort((o1, o2) -> Integer.valueOf(o2.getPrice()).compareTo(o1.getPrice()));
		}
		model.addAttribute("roomTypes", roomTypes);
		
		return "roomTypes";
	}

	@GetMapping("/{id}")
	public String showRoomById(Model model, @PathVariable String id) {

		try {
			int roomTypeId = Integer.parseInt(id);
			RoomType roomType = roomTypeService.findById(roomTypeId);
			if (roomType == null) {
				return "notfound";
			}
			ArrayList<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
			ArrayList<RoomType> otherRoomTypes = new ArrayList<>();
			for (RoomType roomType1 : roomTypes) {
				if (otherRoomTypes.size() == 3) {
					break;
				}
				if (roomType1.getRoomTypeId() != Integer.parseInt(id)) {
					otherRoomTypes.add(roomType1);
				}
			}
			BookedRoom bookedRoom = new BookedRoom();
			bookedRoom.setPrice(roomType.getPrice());
			ArrayList<Service> services = serviceService.findAllService();
			model.addAttribute("services", services);
			model.addAttribute("otherRoomTypes", otherRoomTypes);
			model.addAttribute("bookedRoom", bookedRoom);
			model.addAttribute("roomType", roomType);
			return "roomTypeDetail";
		} catch (Exception e) {
			System.out.println(e);
			return "notfound";
		}

	}
}
