package com.revature.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entity.AccountType;
import com.revature.service.AccountTypeService;

@RestController
@RequestMapping("/acctype")
@CrossOrigin
public class AccountTypeController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	private AccountTypeService acctypeservice;
	
	@Autowired
	public AccountTypeController(AccountTypeService acctypeservice) {
		this.acctypeservice = acctypeservice;
	}
	
//---------------As an admin can add account types to the database---------------
    
    @PostMapping(value="/type")
    public AccountType addAccountType(@RequestBody AccountType accType) {
    	int queryID = (int) (10000*Math.random());
    	MDC.put("POST event", "acctype/type endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");
    	log.debug(accType.getType() + " has been added");
    	MDC.clear();
    	return this.acctypeservice.addAccountType(accType);
    }
	
  //---------------Will pull all of the account types and return a list---------------

    @GetMapping(value="/all")
    public List<AccountType> getAllAccountType() {
    	int queryID = (int) (10000*Math.random());
    	MDC.put("GET event", "acctype/all endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");
    	log.debug("getting all accoutTypes from DB");
    	MDC.clear();
    	return this.acctypeservice.getAllAccTypes();
    }
    
  //---------------Will update a current account type(for spelling errors, ect)---------------
 
    @PutMapping(value="type/u")
    public void updateAccountType(@RequestBody AccountType accType) {
    	int queryID = (int) (10000*Math.random());
    	MDC.put("PUT event", "acctype/type/u endpoint, Event ID: " + queryID);
    	log.info("endpoint accessed");
    	log.debug("accessing account DB to get account");
    	AccountType u = this.acctypeservice.findById(accType.getAccTypeId());
    	log.debug("updating account with passed value");
    	u.setType(accType.getType());
    	log.debug("updating DB");
        this.acctypeservice.addAccountType(u);
    	MDC.clear();
    }
	
}
