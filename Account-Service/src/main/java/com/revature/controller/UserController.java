package com.revature.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entity.User;
import com.revature.exceptions.PasswordIsEmptyException;
import com.revature.service.UserService;
import com.revature.util.Logging;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
    UserService userservice;
    
    // Bcrypt encryption for user password
    BCryptPasswordEncoder encrypt = new BCryptPasswordEncoder();

  //---------------Returns all the users in the database as a List---------------
    @GetMapping(value="/all")
    public List<User> getAllUsers() {
    	int queryID = (int) (10000*Math.random());
    	MDC.put("POST event", "user/all endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");
    	log.debug("DB accessed to gett all users");
    	MDC.clear();
    	return userservice.getAllUsers();
    }

  //---------------Adds a new user to the database upon registration---------------
    @PostMapping(value="/new")
    public User addNewUser(@RequestBody User user) {
    	int queryID = (int) (10000*Math.random());
    	MDC.put("POST event", "user/new endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");
    	log.debug("encrypting new user password");
    	try{
    		user.setPassword(encrypt.encode(user.getPassword()));
    	}catch(IllegalArgumentException ex) {
    		throw new PasswordIsEmptyException("Required request body is incorrect");
    	}
    	log.debug("setting email to all lowercase");
    	log.debug(user.getFirstName() + " " + user.getLastName() + " is being registered.");
    	MDC.clear();
        return this.userservice.addUser(user);
    }
	
  //---------------Compares login credentials with whats in the database---------------
    //Will return null if invalid
    @PostMapping(value="/login")
    public User loginUser(@RequestBody User user) {
    	int queryID = (int) (10000*Math.random());
    	MDC.put("POST event", "user/login endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");
    	log.debug("converting email to lowercase");
    	log.debug("checking if credentials are correct");
    	User current = this.userservice.findUserByEmail(user.getEmail());
    	log.debug("checking if credentials are correct");
    	if(!(this.userservice.existsByEmail(user.getEmail().toLowerCase()) && encrypt.matches(user.getPassword(), current.getPassword()))) {
    		log.debug("password incorect");
    		MDC.clear();
    		return null;
    	} else {
    		log.debug("login successfull");
    		User u = this.userservice.findUserByEmail(user.getEmail().toLowerCase());
    		u.setPassword("*****");
    		Logging.Log4("info", u.getUserId() + " has logged in");
    		MDC.clear();
    		return u;
    	}
    }

  //---------------Will pull a user from database by the id or email---------------
    
    @PostMapping(value="/user")
    public User findUserById(@RequestBody User user) {
    	int queryID = (int) (10000*Math.random());
    	MDC.put("POST event", "user/user endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");
    	log.debug("get the passed in userid and make sure the user exists");
    	User u = this.userservice.findById(user.getUserId());
    	if(u==null) {
    		log.debug("pull user from DB");
    		u = this.userservice.findUserByEmail(user.getEmail().toLowerCase());
    	}
    	log.debug("changing password before sending user to front end for security");
    	u.setPassword("*****");
    	log.debug("returning user");
    	MDC.clear();
    	return u;
    }
    
  //---------------Will Take in new user info and update the user in the database---------------
    @PutMapping(value="/info")
    public void updateInformation(@RequestBody User user) {
    	int queryID = (int) (10000*Math.random());
    	MDC.put("PUT event", "user/info endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");
    	log.debug("getting user with passed ID from DB");
    	User u = this.userservice.findById(user.getUserId());
    	log.debug("updating username");
        u.setEmail(user.getEmail().toLowerCase());
        log.debug("updating first name");
        u.setFirstName(user.getFirstName());
        log.debug("updating last name");
        u.setLastName(user.getLastName());
        log.debug("updating dicount info");
        u.setUserDiscount(user.getUserDiscount());
        if (u.getUserDiscount() > 0) {        	
        	u.setUserDiscounted(true);
        } else {
        	u.setUserDiscounted(false);
        }
        log.info(u.getUserId() + " has updated their information");
        MDC.clear();
        this.userservice.editUser(u);
    }
    
  //---------------Will take in new user password and encrypt before updating database---------------
    @PutMapping(value="/cred")
    public void updatePassword(@RequestBody User user) {
    	int queryID = (int) (10000*Math.random());
    	MDC.put("PUT event", "user/cred endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");
    	log.debug("getting user with passed ID from DB");
    	User u = this.userservice.findById(user.getUserId());
    	log.debug("encripting new password for starage");
    	try {
    		u.setPassword(encrypt.encode(user.getPassword()));
    	}catch(IllegalArgumentException ex) {
    		throw new PasswordIsEmptyException("Required request body is incorrect");
    	}
        log.info(u.getUserId() + " has updated their password");
        MDC.clear();
        this.userservice.editUser(u);
    }
    
  //---------------Will take an image and update it to the database---------------
    @PutMapping(value="/pic")
    public void updateProfilePic(@RequestBody User user) {
    	int queryID = (int) (10000*Math.random());
    	MDC.put("PUT event", "user/pic endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");
    	log.debug("getting user with passed ID from DB");
    	User u = this.userservice.findById(user.getUserId());
    	log.debug("setting new profile pic");
        u.setProfilePic(user.getProfilePic());
        log.info(u.getUserId() + " has updated their profile picture");
        MDC.clear();
        this.userservice.editUser(u);
    }
    
  //---------------Updates user to admin---------------
    @PutMapping(value="/master")
    public void updateIsAdmin(@RequestBody User user) {
    	int queryID = (int) (10000*Math.random());
    	MDC.put("PUT event", "user/master endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");
    	log.debug("getting user with passed ID from DB");
        User u = this.userservice.findById(user.getUserId());
        if(u.isAdmin() == true) {
        	log.info("user is already admin, revoking privlage");
        	u.setAdmin(false);
        	log.debug("saving user");
        	this.userservice.editUser(u);
        } else {
        	log.info("adding admin");
        	u.setAdmin(true);
        	log.debug("updating DB");
        	this.userservice.editUser(u);
        }
       	MDC.clear();
    }
   
}