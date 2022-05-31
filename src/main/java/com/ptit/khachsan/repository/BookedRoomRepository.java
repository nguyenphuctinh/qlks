package com.ptit.khachsan.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ptit.khachsan.models.BookedRoom;
import com.ptit.khachsan.models.Booking;

@Repository
public interface BookedRoomRepository  extends CrudRepository<BookedRoom	, Integer>{
	@Query(value = "SELECT * FROM booked_room  "
			+ "join room "
			+ "on room.ID_ROOM =  booked_room.ID_ROOM "
			+ "where (check_out >=:check_out  and check_in <= :check_in "
			+ "or check_out <=:check_out  and check_in >= :check_in "
			+ "or check_out >=:check_out  and check_in >= :check_in and check_in <= :check_out "
			+ "or check_out <= :check_out and check_in <= :check_in and check_out >=:check_in )"
			+ "and ID_TYPE_ROOM=:room_type_id"
			, nativeQuery = true)
	List<BookedRoom> getExistingBookedRoomBetweenDateById(@Param("check_in") Date checkIn, @Param("check_out") Date checkOut, @Param("room_type_id") int roomTypeId);
	
	@Query(value="SELECT * FROM booked_room "
			+ "where  MONTH(check_out) = :month AND YEAR(check_out) = :year and check_out <CURRENT_DATE() ", nativeQuery = true)
	List<BookedRoom> selectCheckedOutBookedRooms(@Param("month") int month, @Param("year") int year);
	
	@Query(value = "SELECT * FROM booked_room  "
			+ "join room "
			+ "on room.ID_ROOM =  booked_room.ID_ROOM "
			+ "where (check_out >=:check_out  and check_in <= :check_in "
			+ "or check_out <=:check_out  and check_in >= :check_in "
			+ "or check_out >=:check_out  and check_in >= :check_in and check_in <= :check_out "
			+ "or check_out <= :check_out and check_in <= :check_in and check_out >=:check_in )"
			, nativeQuery = true)
	List<BookedRoom> getExistingBookedRoomBetweenDate(@Param("check_in") Date checkIn, @Param("check_out") Date checkOut);
}
