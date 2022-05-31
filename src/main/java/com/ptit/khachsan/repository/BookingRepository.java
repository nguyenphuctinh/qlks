package com.ptit.khachsan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.core.CrudMethods;
import org.springframework.stereotype.Repository;

import com.ptit.khachsan.models.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {
	
}
