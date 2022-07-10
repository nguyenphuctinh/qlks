package com.ptit.khachsan.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ptit.khachsan.models.BookedRoom;
import com.ptit.khachsan.models.Booking;
import com.ptit.khachsan.models.Room;
import com.ptit.khachsan.models.RoomType;
import com.ptit.khachsan.models.RoomTypeImg;
import com.ptit.khachsan.models.Service;
import com.ptit.khachsan.models.User;
import com.ptit.khachsan.services.BookedRoomService;
import com.ptit.khachsan.services.BookingService;
import com.ptit.khachsan.services.RoomService;
import com.ptit.khachsan.services.RoomTypeImgService;
import com.ptit.khachsan.services.RoomTypeService;
import com.ptit.khachsan.services.ServiceService;
import com.ptit.khachsan.services.UserRepositoryUserDetailsService;
import com.ptit.khachsan.services.UserService;
import com.ptit.khachsan.utils.FileUploadUtil;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private RoomService roomService;
	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private RoomTypeImgService roomTypeImgService;
	@Autowired
	private ServiceService serviceService;
	@Autowired
	private UserService userService;
	@Autowired
	private BookingService bookingService;
	@Autowired
	private BookedRoomService bookedRoomService;

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
	public String showAdminPage(Model model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = ((User) principal);
		if (user.getRole().equals("ROLE_RECEPTIONIST")) {

			return "redirect:/admin/receptionist";
		}
		ArrayList<Room> rooms = roomService.getAllRooms();
		model.addAttribute("rooms", rooms);

		return "roomManagement";
	}
	@GetMapping("roomTypeManagement")
	public String showRoomTypeManagement(Model model) {

		ArrayList<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
		
		model.addAttribute("roomTypes", roomTypes);
		
		return "roomTypeManagement";
	}
	@PostMapping("roomTypes/add")
	public String addRoom(Model model, @Valid RoomType roomType, Errors errors,
			@RequestParam("images") MultipartFile[] multipartFiles) throws IOException {
		if (errors.hasErrors()) {

			return "addingRoomTypeForm";
		}
		RoomType newRoomType = roomTypeService.save(roomType);

		List<RoomTypeImg> roomTypeImages = new ArrayList<>();

		for (int i = 0; i < multipartFiles.length; i++) {
			String fileName = StringUtils.cleanPath(multipartFiles[i].getOriginalFilename());
			StringTokenizer stz = new StringTokenizer(fileName, ".");
			Calendar instance = Calendar.getInstance();
			String savedFileName = stz.nextToken() + "" + instance.getTimeInMillis() + "." + stz.nextToken();
			FileUploadUtil.saveFile("src/main/resources/static/img", savedFileName, multipartFiles[i]);
			roomTypeImages.add(roomTypeImgService.save(new RoomTypeImg(newRoomType.getRoomTypeId(), savedFileName)));

		}
		roomType.setRoomTypeImages(roomTypeImages);
		roomTypeService.save(roomType);
		ArrayList<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
		model.addAttribute("roomTypes", roomTypes);
		return "roomTypeManagement";
	}

	@GetMapping("roomTypes/add")
	public String showAddRoomType(Model model) {
		model.addAttribute("roomType", new RoomType());
		return "addingRoomTypeForm";
	}

	@GetMapping("roomTypes/update/{id}")
	public String showUpdateRoomType(Model model, @PathVariable String id) {
		RoomType roomType = roomTypeService.findById(Integer.parseInt(id));
		if (roomType == null) {
			return "redirect:/admin/roomTypeManagement";
		}
		model.addAttribute("roomType", roomType);
		return "updatingRoomTypeForm";
	}

	@PostMapping("roomTypes/update")
	public String updateRoomType(Model model, @Valid RoomType roomType, Errors errors) {
		if (errors.hasErrors()) {

			return "updatingRoomTypeForm";
		}
		roomTypeService.save(roomType);

		return "redirect:/admin/roomTypeManagement";
	}

	@GetMapping("roomTypes/delete/{id}")
	public String showDeleteRoomType(Model model, @PathVariable String id) {
		RoomType roomType = roomTypeService.findById(Integer.parseInt(id));
		if (roomType == null) {
			return "redirect:/admin/roomTypeManagement";
		}
		model.addAttribute("roomType", roomType);
		return "deleteRoomTypeForm";
	}

	@PostMapping("roomTypes/delete/{id}")
	public String deleteRoomType(Model model, @PathVariable String id) {
		RoomType roomType = roomTypeService.findById(Integer.parseInt(id));
		if (roomType == null) {
			return "redirect:/admin/roomTypeManagement";
		}
		roomTypeService.delete(roomType);
		return "redirect:/admin/roomTypeManagement";
	}
	@GetMapping("report")
	public String showReport(Model model, @RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate) {

		if (startDate == null) {
			Date tmp = new Date();
			tmp.setMonth(1);

			startDate = new SimpleDateFormat("yyyy-MM").format(tmp);
		}
		if (endDate == null) {
			Date tmp = new Date();

			endDate = new SimpleDateFormat("yyyy-MM").format(tmp);
		}
		DateFormat formater = new SimpleDateFormat("yyyy-MM");

		Calendar beginCalendar = Calendar.getInstance();
		Calendar finishCalendar = Calendar.getInstance();
		ArrayList<String> months = new ArrayList<String>();
		ArrayList<Long> monthlyRevenues = new ArrayList<>();
		try {
			beginCalendar.setTime(formater.parse(startDate));
			finishCalendar.setTime(formater.parse(endDate));
			while (true) {
				// add one month to date per loop
				String date = formater.format(beginCalendar.getTime()).toUpperCase();

				months.add(date);
				StringTokenizer stz = new StringTokenizer(date, "-");
				ArrayList<BookedRoom> checkedOutBookedRooms = bookedRoomService.selectCheckedOutBookedRooms(
						Integer.parseInt(stz.nextToken()), Integer.parseInt(stz.nextToken()));
				long revenue = 0;
				for (BookedRoom checkedOutBookedRoom : checkedOutBookedRooms) {
					System.out.println(checkedOutBookedRoom);
					revenue += checkedOutBookedRoom.calculateTotalPriceOfUsedServices()
							+ checkedOutBookedRoom.getPrice();
				}

				monthlyRevenues.add(revenue);
				if (beginCalendar.after(finishCalendar)) {
					break;
				}
				beginCalendar.add(Calendar.MONTH, 1);
			}
			ArrayList<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
			model.addAttribute("roomTypes", roomTypes);
			model.addAttribute("months", months);
			model.addAttribute("monthlyRevenues", monthlyRevenues);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			return "report";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "redirect:/";
		}
	}
	@GetMapping("receptionist")
	public String showReceptionist(Model model) {

		ArrayList<Booking> bookings = bookingService.getAllBookings();
		model.addAttribute("bookings", bookings);
		return "receptionist";

	}

	@GetMapping("roomManagement")
	public String showRoomManagement(Model model) {

		ArrayList<Room> rooms = roomService.getAllRooms();
		model.addAttribute("rooms", rooms);

		return "roomManagement";
	}

	@GetMapping("room/update/{id}")
	public String showUpdateRoom(Model model, @PathVariable String id) {

		Room room = roomService.findById(Integer.parseInt(id));
		if (room == null) {
			return "redirect:/admin/roomManagement";
		}

		ArrayList<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
		
		model.addAttribute("roomTypes", roomTypes);
		model.addAttribute("room", room);
		return "updatingRoomForm";
	}
	@PostMapping("rooms/update")
	public String updateRoom(Model model, @Valid Room room, Errors errors) {
		if (errors.hasErrors()) {

			return "updatingRoomForm";
		}
		roomService.save(room);
		return "redirect:/admin/roomManagement";
	}
	@GetMapping("room/delete/{id}")
	public String showDeleteRoom(Model model, @PathVariable String id) {
		Room room  = roomService.findById(Integer.parseInt(id));
		if (room == null) {
			return "redirect:/admin/roomManagement";
		}
		model.addAttribute("room", room);
		return "deleteRoomForm";
	}
	@PostMapping("room/delete/{id}")
	public String deleteRoom(Model model, @PathVariable String id) {
		Room room  = roomService.findById(Integer.parseInt(id));
		if (room == null) {
			return "redirect:/admin/roomManagement";
		}
		roomService.delete(room);
		return "redirect:/admin/roomManagement";
	}
	

	@GetMapping("serviceManagement")
	public String showServiceManagement(Model model) {

		ArrayList<Service> services = serviceService.getAllServices();
		model.addAttribute("services", services);
		return "serviceManagement";
	}

	@GetMapping("userManagement")
	public String userManagement(Model model) {

		ArrayList<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "userManagement";
	}

	@GetMapping("users/update/{id}")
	public String showUpdateUser(Model model, @PathVariable String id) {
		User selectedUser = userService.findById(Integer.parseInt(id));
		System.out.println(selectedUser);
		if (selectedUser == null) {
			return "redirect:/admin/userManagement";
		}
		model.addAttribute("selectedUser", selectedUser);
		return "updatingUserForm";
	}

	@PostMapping("users/update")
	public String updateUser(Model model, User selectedUser) {
		User updatedUser = userService.findById(selectedUser.getUserId());
		updatedUser.setRole(selectedUser.getRole());
		userService.save(updatedUser);
		return "redirect:/admin/userManagement";
	}

	

	@GetMapping("services/add")
	public String showAddService(Model model) {

		model.addAttribute("service", new Service());

		return "addingServiceForm";
	}

	@PostMapping("services/add")
	public String addService(@Valid Service service, Errors errors) {
		if (errors.hasErrors()) {

			return "addingServiceForm";
		}
		serviceService.save(service);
		return "redirect:/admin/serviceManagement";
	}

	@GetMapping("rooms/add")
	public String showAddRoom(Model model) {
		ArrayList<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
		model.addAttribute("roomTypes", roomTypes);
		model.addAttribute("room", new Room());

		return "addingRoomForm";
	}

	@PostMapping("rooms/add")
	public String addRoom(@Valid Room room, Errors errors, Model model) {
		if (errors.hasErrors()) {
			ArrayList<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
			model.addAttribute("roomTypes", roomTypes);
			System.out.println("error");
			return "addingRoomForm";
		}
		System.out.println("ok");
		roomService.save(room);
		return "redirect:/admin/roomManagement";
	}

	

	@GetMapping("services/update/{id}")
	public String showUpdateService(Model model, @PathVariable String id) {
		Service service = serviceService.findById(Integer.parseInt(id));
		if (service == null) {
			return "redirect:/admin/serviceManagement";
		}
		model.addAttribute("service", service);
		return "updatingServiceForm";
	}

	@PostMapping("services/update")
	public String updateService(Model model, @Valid Service service, Errors errors) {
		if (errors.hasErrors()) {

			return "updatingServiceForm";
		}
		ArrayList<Service> services = serviceService.getAllServices();
		model.addAttribute("services", services);
		serviceService.save(service);
		return "serviceManagement";
	}

	@GetMapping("services/delete/{id}")
	public String showDeleteService(Model model, @PathVariable String id) {
		Service service = serviceService.findById(Integer.parseInt(id));
		if (service == null) {
			return "redirect:/admin/serviceManagement";
		}
		model.addAttribute("service", service);
		return "deleteServiceForm";
	}

	@PostMapping("services/delete/{id}")
	public String deleteService(Model model, @PathVariable String id) {
		Service service = serviceService.findById(Integer.parseInt(id));

		if (service == null) {
			return "redirect:/admin/serviceManagement";
		}
		serviceService.delete(service);
		ArrayList<Service> services = serviceService.getAllServices();
		model.addAttribute("services", services);
		return "serviceManagement";
	}

	@GetMapping("receptionist/booking/{id}")
	public String showBooking(Model model, @PathVariable String id) {
		Booking booking = bookingService.findById(Integer.parseInt(id));
		if (booking == null) {
			return "redirect:/admin/receptionist";
		}
		model.addAttribute("booking", booking);
		return "recepBookingDetail";
	}

	@PostMapping("receptionist/booking/update/{id}")
	public String updateBooking(Model model, @PathVariable String id, @RequestParam String isCheckIn) {
		Booking booking = bookingService.findById(Integer.parseInt(id));
		if (booking == null) {
			return "redirect:/admin/receptionist";
		}
		if (isCheckIn.equals("1")) {
			booking.getBookedRooms().get(0).setIsCheckIn(0);
		} else {
			booking.getBookedRooms().get(0).setIsCheckIn(1);
		}
		bookingService.save(booking);

		return "redirect:/admin/receptionist";
	}
}
