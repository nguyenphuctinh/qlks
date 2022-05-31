package com.ptit.khachsan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ptit.khachsan.models.UsedService;

@Repository
public interface UsedServiceRepository extends CrudRepository<UsedService, Integer> {
	
}
