package com.ptit.khachsan.models;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "booked_room")
public class BookedRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "booked_room_id")
	private int bookedRoomId;
	@NotNull
	@Column(name = "check_in")
	private Date checkIn;
	@NotNull
	@Column(name = "check_out")
	private Date checkOut;
	@Min(value=1, message="Số người phải lớn hơn hoặc bằng 1!") 
	@Column(name = "number_of_people")
	private int numberOfPeople;
	@NotNull
	private int price;
	@NotNull
	@Column(name = "is_check_in")
	private int isCheckIn;
	
	@OneToOne
	@JoinColumn(name = "ID_ROOM", referencedColumnName = "ID_ROOM")
	private Room room;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "booked_room_id")
	private List<UsedService> usedServices;

	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "booking_id", insertable = false, updatable = false)
	private Booking booking;

	private long calculateNumberOfDays() {
		long diff = checkOut.getTime() - checkIn.getTime();
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
	}
	public long calculateTotalPriceOfUsedServices() {
		long soNgay = calculateNumberOfDays();
		long totalPrice = 0;
		for(UsedService usedService: usedServices) {
			long price=0;
			if(usedService.getService().getUnit().equals("người/đêm")) {
				price = usedService.getPrice()*numberOfPeople*soNgay;
			}
			if(usedService.getService().getUnit().equals("phòng/đêm")) {
				price = usedService.getPrice()*soNgay;
			}
			
			totalPrice+=price;
		}
		return totalPrice;
	}
}
