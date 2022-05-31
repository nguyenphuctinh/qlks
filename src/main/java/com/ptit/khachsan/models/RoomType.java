package com.ptit.khachsan.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="type_room")
public class RoomType {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_TYPE_ROOM")
	private int roomTypeId;
	@NotNull
	@NotBlank(message = "Tên không được bỏ trống!")
	@Column(name="name_type_room")
	private String roomTypeName;
	@NotNull
	@Min(value=1, message="Số người phải lớn hơn hoặc bằng 1!") 
	@Column(name="max_customer")
	private int maxCustomer;
	@NotNull
	@NotBlank(message = "Mô tả không được bỏ trống!")
	private String description;
	@NotNull
	@Min(value=0, message="Giá phải lớn hơn hoặc bằng 0!") 
	private int price;
	@NotNull
	@OneToMany
	@JoinColumn(name = "ID_TYPE_ROOM")
	private List<RoomTypeImg> roomTypeImages;
	
	
	
	
	
}
