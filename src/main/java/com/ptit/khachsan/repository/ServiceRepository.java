package com.ptit.khachsan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ptit.khachsan.models.RoomType;
import com.ptit.khachsan.models.Service;

@Repository
public interface ServiceRepository extends  CrudRepository<Service, Integer>{

}
