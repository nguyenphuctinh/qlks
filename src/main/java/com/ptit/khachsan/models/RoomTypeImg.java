package com.ptit.khachsan.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="type_room_img")
public class RoomTypeImg {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="type_room_img_id")
	private int roomTypeImgId;
	@Column(name="ID_TYPE_ROOM")
	private int roomTypeId;
	@Column(name="img")
	private String roomTypeImg;
	public RoomTypeImg() {
	
	}
	
	public RoomTypeImg(int roomTypeId, String roomImg) {
		super();
		this.roomTypeId = roomTypeId;
		this.roomTypeImg = roomImg;
	}
	
}
