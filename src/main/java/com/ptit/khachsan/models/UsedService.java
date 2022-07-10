package com.ptit.khachsan.models;

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
import lombok.ToString;

@Data
@Entity
@Table(name = "used_service")
public class UsedService {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int usedServiceId;
	@NotNull
	private int  price;
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "booked_room_id", insertable = false, updatable = false)
	private BookedRoom bookedRoom;
	@OneToOne
	@JoinColumn(name = "service_id", referencedColumnName = "id")
	private Service service;
	public UsedService() {
	}
	public UsedService( int price, Service service) {
		super();
		this.price = price;
		this.service = service;
	}

}
