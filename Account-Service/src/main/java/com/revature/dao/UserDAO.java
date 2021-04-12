package com.revature.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
	
	//login validation dao method
	public boolean existsByEmail(String email);
	//getting user information
	public Optional<User> findUserByEmail(String email);
	public Optional<User> findUserByUserId(int userId);
}
