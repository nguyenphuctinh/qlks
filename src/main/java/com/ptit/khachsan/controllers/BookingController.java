package com.ptit.khachsan.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ptit.khachsan.models.BookedRoom;
import com.ptit.khachsan.models.Booking;
import com.ptit.khachsan.models.Room;
import com.ptit.khachsan.models.RoomType;
import com.ptit.khachsan.models.Service;
import com.ptit.khachsan.models.UsedService;
import com.ptit.khachsan.models.User;
import com.ptit.khachsan.services.BookedRoomService;
import com.ptit.khachsan.services.BookingService;
import com.ptit.khachsan.services.RoomService;
import com.ptit.khachsan.services.RoomTypeService;
import com.ptit.khachsan.services.ServiceService;
import com.ptit.khachsan.services.UsedServiceService;

@Controller
@RequestMapping("/booking")
@SessionAttributes("booking")
public class BookingController {
	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private RoomService roomService;
	@Autowired
	private ServiceService serviceService;
	@Autowired
	private BookingService bookingService;
	@Autowired
	private BookedRoomService bookedRoomService;
	@Autowired
	private UsedServiceService usedServiceService;

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

	@ModelAttribute(name = "booking")
	public Booking booking() {
		return new Booking();
	}

	@PostMapping
	public String book(Model model, @ModelAttribute Booking booking) {

		booking.setBookingDate(new Date());
		bookingService.save(booking);

		model.addAttribute("message", "Đặt phòng thành công!");
		return "booking";
	}

	@GetMapping
	public String showBooking(Model model, BookedRoom bookedRoom, @RequestParam(value = "services", required=false) List<Service> services,

			@RequestParam String roomTypeId, @ModelAttribute Booking booking, RedirectAttributes ra

	) {

		ArrayList<BookedRoom> existingBookedRooms = bookedRoomService.getExistingBookedRoomBetweenDateById(
				bookedRoom.getCheckIn(), bookedRoom.getCheckOut(), Integer.parseInt(roomTypeId));
		ArrayList<Integer> unavailableRoomId = new ArrayList<>();
		for (BookedRoom tmp : existingBookedRooms) {
			unavailableRoomId.add(tmp.getRoom().getRoomId());
		}
		ArrayList<Room> availableRooms = new ArrayList<Room>();
		ArrayList<Room> allRooms = roomService.getAllRooms();
		for (Room tmp : allRooms) {
			if (!unavailableRoomId.contains(tmp.getRoomId())
					&& tmp.getRoomType().getRoomTypeId() == Integer.parseInt(roomTypeId)) {
				availableRooms.add(tmp);
			}
		}
	//	System.out.println( bookedRoom.getCheckIn().compareTo(new Date()));
		if(bookedRoom.getCheckIn().compareTo(bookedRoom.getCheckOut())>0 || bookedRoom.getCheckIn().compareTo(new Date())<0 ) {
			ra.addFlashAttribute("error", "Thời gian không hợp lệ");
			return "redirect:/roomTypes/" + roomTypeId;
		}
		RoomType roomType = roomTypeService.findById(Integer.parseInt(roomTypeId));
		if(bookedRoom.getNumberOfPeople()>roomType.getMaxCustomer() || bookedRoom.getNumberOfPeople()<=0) {
			ra.addFlashAttribute("error", "Số người không hợp lệ!");
			return "redirect:/roomTypes/" + roomTypeId;
		}
		if (availableRooms.size() == 0) {
			ra.addFlashAttribute("error", "Hiện tại không còn phòng trong thời gian này!");
			return "redirect:/roomTypes/" + roomTypeId;
		}
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = ((User) principal);

		booking.setUser(user);
		ArrayList<UsedService> usedServices = new ArrayList<>();
		if(services!=null) {
			for (Service service : services) {
				usedServices.add(new UsedService( service.getPrice(), service));
			}
		}
		
		bookedRoom.setRoom(availableRooms.get(0));
		bookedRoom.setUsedServices(usedServices);
		ArrayList<BookedRoom> bookedRooms = new ArrayList<BookedRoom>();
		bookedRooms.add(bookedRoom);
		booking.setBookedRooms(bookedRooms);
		model.addAttribute("booking", booking);
		return "booking";
	}
}
