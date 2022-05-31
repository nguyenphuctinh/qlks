package com.ptit.khachsan.models;



import java.util.Date;
import java.util.List;

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
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="booking")
public class Booking {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="booking_id")
	private int bookingId;
	@NotNull
	@Column(name="booking_date")
	private Date bookingDate;
	private String note;
	@ManyToOne
    @JoinColumn(name = "user_id")
	private User user;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "booking_id")
	private List<BookedRoom> bookedRooms;
	
	public long calculateBookingPrice() {
		long totalPrice=0;
		for(BookedRoom bookedRoom: bookedRooms) {
			totalPrice+=bookedRoom.getPrice()+bookedRoom.calculateTotalPriceOfUsedServices();
		}
		return totalPrice;
	}
}
