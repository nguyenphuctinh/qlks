package com.ptit.khachsan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ptit.khachsan.models.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {

}
