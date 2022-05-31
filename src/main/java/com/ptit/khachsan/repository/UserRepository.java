package com.ptit.khachsan.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ptit.khachsan.models.User;
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
  
}
