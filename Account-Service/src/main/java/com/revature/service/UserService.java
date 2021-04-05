package com.revature.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.UserDAO;
import com.revature.entity.User;

@Service
public class UserService {

	
	private UserDAO userdao;
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	public UserService(UserDAO userdao) {
		this.userdao = userdao;
	}
	
    public List<User> getAllUsers() {
    	MDC.put("event", "select");
    	logger.info("Finding all users");
    	MDC.clear();
        return this.userdao.findAll();
    }

    public User addUser(User user) {
    	MDC.put("event", "create");
    	logger.info("Creating user");
    	MDC.clear();
        return this.userdao.save(user);
    }
	
    public boolean existsByEmail(String email) {
    	return this.userdao.existsByEmail(email);
    }
   
    public User findUserByEmail(String email) {
    	MDC.put("event", "select");
    	MDC.put("email", email);
    	logger.info("Finding user by email");
    	MDC.clear();
    	return this.userdao.findUserByEmail(email);
    }
    
    public User findById(int userId) {
    	MDC.put("event", "select");
    	MDC.put("user id", Integer.toString(userId));
    	logger.info("Finding user by id");
    	MDC.clear();
        return this.userdao.findUserByUserId(userId);
    }
    
}
