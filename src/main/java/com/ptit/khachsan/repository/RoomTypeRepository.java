package com.ptit.khachsan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ptit.khachsan.models.Room;
import com.ptit.khachsan.models.RoomType;
@Repository
public interface RoomTypeRepository extends CrudRepository<RoomType, Integer> {

}
