package com.ptit.khachsan.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "service")
public class Service {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int serviceId;
	@NotNull
	@NotBlank(message = "Tên không được bỏ trống!")
	private String name;
	@NotNull
	@NotBlank(message = "Đơn vị không được bỏ trống!")
	private String unit;
	@NotNull
	@Min(value = 0, message = "Giá phải lớn hơn hoặc bằng 0!")
	private int price;
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "service_id", insertable = false, updatable = false)
	private UsedService usedService;
}
