package com.revature.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
//TODO: decide weather we should remove this import and related unused field
//import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entity.Account;
import com.revature.entity.User;
import com.revature.service.AccountService;


@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	private AccountService accservice;

	@Autowired
	public AccountController(AccountService accservice) {
		this.accservice = accservice;
	}
	
//	@Autowired
//	private DiscoveryClient discoveryClient;

//---------------Takes in the new account point total and saves it to database---------------

    @PutMapping(value="/points")
    public void updatePoints(@RequestBody Account acc) {
    	int queryID = (int) (10000*Math.random());
    	MDC.put("PUT event", "account/points endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");
    	
    	log.debug("accessing DB and getting account speciphied in request body");
    	Account a = this.accservice.findById(acc.getAccId());
        log.debug("assigning points");
        a.setPoints(acc.getPoints());
        log.debug("User with ID: " + a.getUserId() + " has had their points updated");
        this.accservice.addAccount(a);
        MDC.clear();
    }

  //---------------Will take the new points and add them to the account point total---------------

    @PutMapping(value="/points/a")
    public void addPoints(@RequestBody Account acc) {
    	int queryID = (int) (10000*Math.random());
    	MDC.put("PUT event", "account/points/a endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");

    	log.debug("accessing DB and getting account spesified in request body");
    	Account a = this.accservice.findById(acc.getAccId());
        a.setPoints(a.getPoints() + acc.getPoints());
        log.debug(acc.getPoints() + " points added to account with ID: " + a.getUserId());
        this.accservice.addAccount(a);
        MDC.clear();
    }


  //---------------Takes in the new account and adds it to the database---------------

    @PostMapping(value="/new")
    public Account addAccount(@RequestBody Account acc) {
    	int queryID = (int) (10000*Math.random());
    	MDC.put("POST event", "account/new endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");
    	
    	log.debug("account added with ID: " + acc.getAccId());
        MDC.clear();
        return this.accservice.addAccount(acc);
        
    }

  //---------------Will return the account, from the database, by the id---------------

    @PostMapping(value="/account")
    public Account getAccountById(@RequestBody Account acc) {
    	//TODO: change this endpoint to use 'get' method?
    	int queryID = (int) (10000*Math.random());
    	MDC.put("POST event", "account/account endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");
    	log.debug("accessing DB to find an account by provided ID number");
    	Account a = this.accservice.findById(acc.getAccId());
    	if(a != null) {
    		log.debug("account found by ID");
    	} else {
    		log.debug("account NOT found by ID");

    	}
    	log.debug("returning account found");
    	MDC.clear();
    	return a;
    }

  //---------------Will return accounts related to the user id and return a list---------------

    @PostMapping(value="/accounts")
    public List<Account> findAccountByUserId(@RequestBody User acc) {
    	//TODO: change this endpoint to use 'get' method?
    	int queryID = (int) (10000*Math.random());
    	MDC.put("POST event", "account/accounts endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");
    	log.debug("accessing DB to find all accounts");
    	MDC.clear();
    	return this.accservice.findAccountById(acc.getUserId());
    }

}
