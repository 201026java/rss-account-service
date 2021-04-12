package com.revature.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.UserDAO;
import com.revature.entity.Account;
import com.revature.entity.User;
import com.revature.exceptions.EmailAlreadyExistsException;
import com.revature.exceptions.InvalidNewUserException;
import com.revature.exceptions.UserEmailValueEmptyException;
import com.revature.exceptions.UserNotFoundException;

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
    	if(user.getEmail()==null)
    		throw new InvalidNewUserException("Required request body is incorrect");
    	
    	user.setEmail(user.getEmail().toLowerCase());
    	if(userdao.existsByEmail(user.getEmail()))
    		throw new EmailAlreadyExistsException("Email already taken");
    	
    	
        return this.userdao.save(user);
    }
    
    public User editUser(User user) {
    	MDC.put("event", "edit");
    	logger.info("Editing user");
    	MDC.clear();
    	return this.userdao.save(user);
    }
	
    public boolean existsByEmail(String email) {
    	if(email==null)
    		throw new UserEmailValueEmptyException("The field value of email is empty");
    	return this.userdao.existsByEmail(email.toLowerCase());
    }
   
    public User findUserByEmail(String email) {
    	MDC.put("event", "select");
    	MDC.put("email", email);
    	logger.info("Finding user by email");
    	MDC.clear();
    	return this.userdao.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email:" + email + "; dose not exist"));
    }
    
    public User findById(int userId) {
    	MDC.put("event", "select");
    	MDC.put("user id", Integer.toString(userId));
    	logger.info("Finding user by id");
    	MDC.clear();
        return this.userdao.findUserByUserId(userId).orElseThrow(() -> new UserNotFoundException("User with id:" + userId + "; dose not exist"));
    }
    
}
