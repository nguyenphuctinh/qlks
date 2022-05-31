package com.ptit.khachsan.models;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.List;
import lombok.Data;
import lombok.ToString;
@Data
@Entity
@Table(name="room")
public class Room {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_ROOM")
	private int roomId;
	@NotNull
	@NotBlank(message = "Tên phòng không được bỏ trống!")
	@Column(name="name_room")
	private String roomName;
	@ManyToOne
    @JoinColumn(name = "ID_TYPE_ROOM")
	private RoomType roomType;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ROOM", insertable = false, updatable = false)
    private BookedRoom bookedRoom;
}	
