package com.ptit.khachsan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ptit.khachsan.models.Room;
import com.ptit.khachsan.models.RoomTypeImg;
@Repository
public interface RoomTypeImgRepository  extends CrudRepository<RoomTypeImg, Integer> {

}
